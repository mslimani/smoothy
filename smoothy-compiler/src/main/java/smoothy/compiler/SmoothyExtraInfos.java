package smoothy.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import smoothy.BindExtra;

/**
 * Created by mehdi on 19/10/2015.
 */
public class SmoothyExtraInfos {


    private TypeElement mTypeElement;
    private List<SmoothyEtraVariable> mPropertiesElements;

    public SmoothyExtraInfos(TypeElement typeElement) {
        mTypeElement = typeElement;
        mPropertiesElements = new ArrayList<>();
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public List<SmoothyEtraVariable> getPropertiesElements() {
        return mPropertiesElements;
    }

    public void addVariable(VariableElement variableElement) {
        mPropertiesElements.add(new SmoothyEtraVariable(variableElement));
    }

    private List<SmoothyEtraVariable> getVariables(boolean optional) {
        List<SmoothyEtraVariable> variables = new ArrayList<>();
        for (SmoothyEtraVariable propertiesElement : mPropertiesElements) {
            if (propertiesElement.isOptional() == optional) {
                variables.add(propertiesElement);
            }
        }
        return variables;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Parent : ")
                .append(mTypeElement.getSimpleName());
        for (SmoothyEtraVariable propertiesElement : mPropertiesElements) {
            builder.append("\n")
                    .append("Property : ")
                    .append(propertiesElement.getVariableName())
                    .append("\n")
                    .append("Setter : ")
                    .append(propertiesElement.getSetterName());
        }
        return builder.toString();
    }

    public static class SmoothyEtraVariable {

        private VariableElement mVariableElement;

        public SmoothyEtraVariable(VariableElement variableElement) {
            mVariableElement = variableElement;
        }

        public VariableElement getVariableElement() {
            return mVariableElement;
        }

        public String getVariableName() {
            return mVariableElement.getSimpleName().toString();
        }

        public String getSetterName() {
            String name = getVariableName();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                char character = name.charAt(i);
                if (i == 0 && character == 'm') {
                    continue;
                }

                if (i == 1) {
                    builder.append(Character.toLowerCase(character));
                } else {
                    builder.append(character);
                }
            }
            return builder.toString();
        }

        public boolean isOptional() {
            return mVariableElement.getAnnotation(BindExtra.class).optional();
        }

    }
}
