package model.promela.literal.op;
public class Equal extends IBinarop {
private final String EQUAL="==";
public String toCode(){
return EQUAL;
}
}