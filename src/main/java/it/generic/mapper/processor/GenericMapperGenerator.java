package it.generic.mapper.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import it.generic.mapper.common.BaseMapper;
import it.generic.mapper.model.GenericAbstractMapper;
import it.generic.mapper.model.Pojo;


@SupportedAnnotationTypes( "it.generic.mapper.model.Pojo" )
public class GenericMapperGenerator extends AbstractProcessor {
	
	private Filer filer;
    private List<Item> items = new ArrayList<>(  );

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		this.filer = processingEnv.getFiler();
		Messager messager = processingEnv.getMessager();
        Elements elementUtils = processingEnv.getElementUtils();
        Types typeUtils = processingEnv.getTypeUtils();

        TypeMirror standardMapperType =
                        typeUtils.erasure( elementUtils.getTypeElement( BaseMapper.class.getName() ).asType() );
        TypeMirror pojoType = elementUtils.getTypeElement( Pojo.class.getName() ).asType();


        for ( TypeElement annotation : annotations ) {

            if ( typeUtils.isSameType( pojoType, annotation.asType() ) ) {
                Iterator<? extends Element> i = roundEnv.getElementsAnnotatedWith( annotation ).iterator();
                while ( i.hasNext() ) {
                    Element mapperCandidateElement = i.next();
                    TypeMirror mapperCandidateType = mapperCandidateElement.asType();
                    if ( typeUtils.isAssignable( mapperCandidateType, standardMapperType ) ) {
                        items.add( toItem( (DeclaredType ) mapperCandidateType ) );
                    }
                    else {
                        messager.printMessage(
                                        Diagnostic.Kind.WARNING,
                                        "@Pojo annotated class should implement a BaseMapper",
                                        annotation
                        );
                    }
                }

            }
        }
        if ( roundEnv.processingOver() ) {
            write( elementUtils.getTypeElement( GenericAbstractMapper.class.getName() ) );
        }
        return true;
	}
	
	private Item toItem(DeclaredType mapper) {
        Item result = null;
        for ( Element element : mapper.asElement().getEnclosedElements() ) {
            if ( element.getKind() == ElementKind.METHOD && "map".equals( element.getSimpleName().toString() ) ) {
                ExecutableElement method = (ExecutableElement) element;
                result = new Item( (DeclaredType) method.getParameters().get( 0 ).asType(),
                                (DeclaredType) method.getReturnType(),
                                mapper
                );
            }
        }
        return result;
    }

    private void write(TypeElement mapperElement) {
        try (Writer writer = filer.createSourceFile( "it.generic.mapper.common.GenericBeansMapper", mapperElement )
                                  .openWriter()) {
            Configuration cfg = new Configuration( new Version( "2.3.21" ) );
            cfg.setClassForTemplateLoading( GenericMapperGenerator.class, "/" );
            cfg.setDefaultEncoding( "UTF-8" );

            Map<String, Object> templateData = new HashMap<>();
            
            templateData.put( "items", items );
            Template template = cfg.getTemplate( "GenericBeansMapper.ftl" );
            template.process( templateData, writer );
        }
        catch ( TemplateException | IOException ex ) {
            throw new IllegalStateException( ex );
        }
    }

}
