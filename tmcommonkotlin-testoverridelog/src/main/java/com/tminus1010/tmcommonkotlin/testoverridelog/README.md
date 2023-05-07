This module is meant to be implemented in the test source set.

It overrides android.util.Log, which - in the test source set, depending on your build.gradle - will either do nothing or crash with error:
    java.lang.RuntimeException: Method i in android.util.Log not mocked. See http://g.co/androidstudio/not-mocked for details.