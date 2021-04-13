package purposeful;

import util.DebugPrinter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Reflector {

    private Object o;
    private Map<String, Method> setMethodMap;
    private Map<String, Method> getMethodMap;

    public Reflector(Object o) {
        this.o = o;
        setMethodMap = Arrays.stream(o.getClass().getDeclaredMethods())
                .filter(x -> x.getName().startsWith("set"))
                .collect(Collectors.toMap(a -> a.getName().substring(3).toLowerCase(), Function.identity()));
        getMethodMap = Arrays.stream(o.getClass().getDeclaredMethods())
                .filter(x -> x.getName().startsWith("get"))
                .collect(Collectors.toMap(a -> a.getName().substring(3).toLowerCase(), Function.identity()));
    }

    public void reflect(String member, String value) {
        if (value.isEmpty()) {
            return;
        }

        Method method = setMethodMap.get(member.toLowerCase(Locale.ROOT));
        if (method == null) {
            DebugPrinter.error(DebugPrinter.Key.MISC,
                    "Unable to find setter for " + member + " in " + o.getClass().toString());
        }
        try {
            Class type = method.getParameterTypes()[0];
            if (type == Integer.class || type == int.class) {
                Integer evalVal = evalInt(member, value);
                method.invoke(o, evalVal);
            } else {
                DebugPrinter.error(DebugPrinter.Key.MISC,
                        "Unable to match Datatype " + type + " in method " +
                                method.getName() + " of class " + o.getClass().toString());
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer evalInt(String key, String value) {
        String validString = value.replace("+", "");
        String coef[] = validString.split("x");
        boolean linear = validString.contains("x");
        if(linear)
        {
            Double linearCoef = coef.length == 0 || coef[0].isEmpty() ? 1 : Double.parseDouble(coef[0]);
            Integer constant = coef.length < 2 ? 0 : Integer.parseInt(coef[1]);
            return (int) Math.round(linearCoef * getValue(key)) + constant;
        }
        else
        {
            return Integer.parseInt(coef[0]);
        }
    }

    public Integer getValue(String member) {
        Method method = getMethodMap.get(member.toLowerCase(Locale.ROOT));
        if (method == null) {
            DebugPrinter.error(DebugPrinter.Key.MISC,
                    "Unable to find getter for " + member + " in " + o.getClass().toString());
        }
        try {
            return (Integer) method.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void reflect(List<String> members, List<String> values) {
        for (int x = 0; x < Math.min(members.size(), values.size()); x++) {
            reflect(members.get(x), values.get(x));
        }
    }
}
