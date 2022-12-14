package saber.api.common;

public class PropertiesUtility
{
    public static int getIntegerOrDefault(String property, int defaultProperty)
    {
        return property == null || !property.chars().allMatch(Character::isDigit) ? defaultProperty
                : Integer.parseInt(property);
    }

    public static boolean getBooleanOrDefault(String property, boolean defaultProperty)
    {
        return Boolean.TRUE.toString().equals(property) || (!Boolean.FALSE.toString().equals(property) && defaultProperty);
    }
}
