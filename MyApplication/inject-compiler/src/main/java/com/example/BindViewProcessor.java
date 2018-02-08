package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * data:2018/2/7 on 17:18
 * author:JiaoPeiRong
 */

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
    //element
    private Elements elementUtils;
    //
    private Types typeUtils;
    //java file
    private Filer FilerUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        FilerUtils = processingEnv.getFiler();
    }

    /**
     * annotation
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //app TypeElement -- MainActivity
        Map<TypeElement, List<FieldViewBinding>> targeMap = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
//            Log.i,System.out.print
            //
            FileUtils.print("elment --->" + element.getSimpleName().toString());
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            List<FieldViewBinding> list = targeMap.get(enclosingElement);
            if (null == list) {
                list = new ArrayList<>();
                targeMap.put(enclosingElement, list);
            }
            //packageName
            String packageName = getPackageName(enclosingElement);
            int id = element.getAnnotation(BindView.class).value();
            String fieldName = element.getSimpleName().toString();
            TypeMirror typeMirror = element.asType();
            FileUtils.print("-------" + typeMirror.getKind());
            FieldViewBinding fieldViewBinding = new FieldViewBinding(fieldName, typeMirror, id);
            list.add(fieldViewBinding);
        }

        for (Map.Entry<TypeElement, List<FieldViewBinding>> item : targeMap.entrySet()) {
            List<FieldViewBinding> list = item.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }
            //activity
            TypeElement enClosingElement = item.getKey();
            String packageName = getPackageName(enClosingElement);
            //MainActivity
            String complite = getClassName(enClosingElement, packageName);
            ClassName className = ClassName.bestGuess(complite);
            ClassName viewBinder = ClassName.get("com.jpr.inject", "ViewBinder");

            //pin jie java lei
            TypeSpec.Builder builder = TypeSpec.classBuilder(complite + "$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T", className))
                    .addSuperinterface(ParameterizedTypeName.get(viewBinder, className));
            //pin jie fuction
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addAnnotation(Override.class)
                    .addParameter(className, "target", Modifier.FINAL);
            //function neirong
            for (int i = 0; i < list.size(); i++) {
                FieldViewBinding fieldViewBinding = list.get(i);
                //anroid.text.TextView
                String pacckageNameString = fieldViewBinding.getType().toString();
                ClassName viewClass = ClassName.bestGuess(pacckageNameString);
                methodBuilder.addStatement("target.$L=($T)target.findViewById($L)",fieldViewBinding.
                        getName(),viewClass,fieldViewBinding.getResId());
            }

            builder.addMethod(methodBuilder.build());
            try {
                JavaFile.builder(packageName,builder.build())
                        .addFileComment("auto create make")
                        .build()
                        .writeTo(FilerUtils);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    //Mainactivity$$ViewBinder
    private String getClassName(TypeElement enClosingElement, String packageName) {
        int packageLength = packageName.length() + 1;

        return enClosingElement.getQualifiedName().toString().substring(packageLength).replace(".", "$");
    }


    private String getPackageName(TypeElement enClosingElement) {
        return elementUtils.getPackageOf(enClosingElement).getQualifiedName().toString();
    }
}
