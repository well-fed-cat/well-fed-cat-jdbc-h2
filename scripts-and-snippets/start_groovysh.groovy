class start_groovysh {

    public static void main(String[] args) {

        final String JAVA_HOME = "C:\\d\\Apps\\amazon-corretto-16.0.0.36.1-windows-x64-jdk\\jdk16.0.0_36"
        final String JAVA_BIN = "C:\\d\\Apps\\amazon-corretto-16.0.0.36.1-windows-x64-jdk\\jdk16.0.0_36\\bin"

        var groovy.lang.Closure findKeyInMapStringStringCaseInsensitive = { map, searchTerm ->
            for (String key : map.keySet()) {
                if (searchTerm.equalsIgnoreCase(key)) {
                    return key
                }
            }
            return null
        }

        ProcessBuilder processBuilder = new ProcessBuilder()

        final Map<String, String> environment = processBuilder.environment()
        final String javaHomeKey = findKeyInMapStringStringCaseInsensitive(environment, "JAVA_HOME") ?: "JAVA_HOME"
        final String pathKey = findKeyInMapStringStringCaseInsensitive(environment, "PATH") ?: "PATH";

        environment.put(javaHomeKey, JAVA_HOME)
        environment.put(pathKey, JAVA_BIN + ";" + environment.get(pathKey))

        processBuilder.command(
                "C:\\d\\Apps\\groovy-3.0.7\\bin\\groovysh.bat",
                    "-cp",
                    "../well-fed-cat-core-root/well-fed-cat-app-sample-db-h2/build/install/well-fed-cat-app-sample-db-h2/lib/*"
                )
                .inheritIO()
                .start()
                .waitFor();
    }

//    private String findKeyInMapStringStringCaseInsensitive(final ProcessEnvironment map, final String searchTerm) {
//        for (String key : map.keySet()) {
//            if (searchTerm.equalsIgnoreCase(key)) {
//                return key
//            }
//        }
//        return null
//    }
}
