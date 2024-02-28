package ch.basler.playground;

public interface MyTest {
  public static final String JNDI_NAME = "basler/MyTestBean_v1_0";

  String executePublicMethod(String inputValue);

  String executeProtectedMethod(String inputValue);

}
