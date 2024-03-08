package ch.basler.playground;

public interface UserInformation {
  public static final String JNDI_NAME = "basler/UserInformation_v1_0";

  String executePublicMethod(String inputValue);

  String executeProtectedMethod(String inputValue);

}
