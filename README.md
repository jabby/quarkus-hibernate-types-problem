# Project to illustrate problem between hibernate-types and quarkus


I have a problem between quarkus and hibernate-types. 
If I use the `jsonb` type definition from hibernate-types, the native build failed. 

You can find a sample project to illustrate the problem. 
Here is all the tests and result I get when I build in native mode.

## Verify application works properly in `quarkus:dev` mode 

### Create and launch `test-db`. 

```
docker run -d --name test-db -e "POSTGRES_DB=test-db" -e "POSTGRES_USER=test" -e "POSTGRES_PASSWORD=test" -p 5432:5432 postgres:12
```

### Launch the application

```
./mvnw compile quarkus:dev
```

### Call the endpoint

```
curl http://localhost:8080/errors
```

## Build failed

### First problem build with GraalVM 19.3.1 and Java 11

I use sdkman to manage my different java sdk. 
In my terminal, I exported the following env variables.

```
export GRAALVM_HOME=$HOME/.sdkman/candidates/java/19.3.1.r11-grl
export JAVA_HOME=${GRAALVM_HOME}                               
export PATH=${GRAALVM_HOME}/bin:$PATH 
```

When I checked which version of Java will be used for the build

```
java -version

openjdk version "11.0.6" 2020-01-14
OpenJDK Runtime Environment GraalVM CE 19.3.1 (build 11.0.6+9-jvmci-19.3-b07)
OpenJDK 64-Bit Server VM GraalVM CE 19.3.1 (build 11.0.6+9-jvmci-19.3-b07, mixed mode, sharing)
```

Then I launched the build with the following command.

```
./mvnw clean package -Pnative -DskipTests=true -e
```
 
It failed.


<details>
    <summary>Complete Trace</summary>
    
```
[INFO] Error stacktraces are turned on.
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< org.acme:quarkus-hibernate-types-problem >--------------
[INFO] Building quarkus-hibernate-types-problem 1.0.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ quarkus-hibernate-types-problem ---
[INFO] Deleting /home/jabberwock/git/github/quarkus-hibernate-types-problem/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ quarkus-hibernate-types-problem ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 3 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ quarkus-hibernate-types-problem ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 3 source files to /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ quarkus-hibernate-types-problem ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/jabberwock/git/github/quarkus-hibernate-types-problem/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ quarkus-hibernate-types-problem ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ quarkus-hibernate-types-problem ---
[INFO] Tests are skipped.
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ quarkus-hibernate-types-problem ---
[INFO] Building jar: /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT.jar
[INFO] 
[INFO] --- quarkus-maven-plugin:1.2.1.Final:build (default) @ quarkus-hibernate-types-problem ---
[INFO] [org.jboss.threads] JBoss Threads version 3.0.0.Final
[INFO] [org.hibernate.jpa.boot.internal.PersistenceXmlParser] HHH000318: Could not find any META-INF/persistence.xml file in the classpath
[INFO] [org.hibernate.Version] HHH000412: Hibernate Core {5.4.10.Final}
[INFO] [io.quarkus.deployment.pkg.steps.JarResultBuildStep] Building native image source jar: /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-native-image-source-jar/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Building native image from /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-native-image-source-jar/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Running Quarkus native-image plugin on GraalVM Version 19.3.1 CE
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] /home/jabberwock/.sdkman/candidates/java/19.3.1.r11-grl/bin/native-image -J-Dsun.nio.ch.maxUpdateArraySize=100 -J-DCoordinatorEnvironmentBean.transactionStatusManagerEnable=false -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-Dvertx.logger-delegate-factory-class-name=io.quarkus.vertx.core.runtime.VertxLogDelegateFactory -J-Dvertx.disableDnsResolver=true -J-Dio.netty.leakDetection.level=DISABLED -J-Dio.netty.allocator.maxOrder=1 --initialize-at-build-time= -H:InitialCollectionPolicy=com.oracle.svm.core.genscavenge.CollectionPolicy$BySpaceAndTime -jar quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar -H:FallbackThreshold=0 -H:+ReportExceptionStackTraces -H:-AddAllCharsets -H:EnableURLProtocols=http,https --enable-all-security-services -H:NativeLinkerOption=-no-pie -H:+JNI --no-server -H:-UseServiceLoaderFeature -H:+StackTrace quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:10190]    classlist:   9,972.55 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:10190]        (cap):     734.22 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:10190]        setup:   2,281.92 ms
10:28:28,498 INFO  [org.hib.Version] HHH000412: Hibernate Core {5.4.10.Final}
10:28:28,519 WARN  [Hibernate Types] You should use Hypersistence Optimizer to speed up your Hibernate application!
10:28:28,519 WARN  [Hibernate Types] For more details, go to https://vladmihalcea.com/hypersistence-optimizer/
10:28:28,519 INFO  [Hibernate Types] 
 _    _                           _     _
| |  | |                         (_)   | |
| |__| |_   _ _ __   ___ _ __ ___ _ ___| |_ ___ _ __   ___ ___
|  __  | | | | '_ \ / _ \ '__/ __| / __| __/ _ \ '_ \ / __/ _ \
| |  | | |_| | |_) |  __/ |  \__ \ \__ \ ||  __/ | | | (_|  __/
|_|  |_|\__, | .__/ \___|_|  |___/_|___/\__\___|_| |_|\___\___|
         __/ | |
        |___/|_|

           ____        _   _           _
          / __ \      | | (_)         (_)
         | |  | |_ __ | |_ _ _ __ ___  _ _______ _ __
         | |  | | '_ \| __| | '_ ` _ \| |_  / _ \ '__|
         | |__| | |_) | |_| | | | | | | |/ /  __/ |
          \____/| .__/ \__|_|_| |_| |_|_/___\___|_|
                | |
                |_|

10:28:28,519 INFO  [Hibernate Types] Check out the README page for more info about the Hypersistence Optimizer banner https://github.com/vladmihalcea/hibernate-types#how-to-remove-the-hypersistence-optimizer-banner-from-the-log
10:28:29,200 INFO  [org.hib.ann.com.Version] HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
10:28:29,227 INFO  [org.hib.dia.Dialect] HHH000400: Using dialect: io.quarkus.hibernate.orm.runtime.dialect.QuarkusPostgreSQL95Dialect
10:28:30,761 INFO  [org.jbo.threads] JBoss Threads version 3.0.0.Final
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:10190]     analysis:  43,367.64 ms
Fatal error: com.oracle.graal.pointsto.util.AnalysisError$ParsingError: Error encountered while parsing com.oracle.svm.reflect.Class_getNestHost_d0409f1154f6242e625526eadd05fbcd60e7d7e9.invoke(java.lang.Object, java.lang.Object[]) 
Parsing context:
	parsing java.lang.reflect.Method.invoke(Method.java:566)
	parsing javax.enterprise.util.AnnotationLiteral.invoke(AnnotationLiteral.java:288)
	parsing javax.enterprise.util.AnnotationLiteral.getMemberValue(AnnotationLiteral.java:276)
	parsing javax.enterprise.util.AnnotationLiteral.equals(AnnotationLiteral.java:199)
	parsing java.util.Objects.equals(Objects.java:77)
	parsing java.util.Arrays.equals(Arrays.java:3187)
	parsing org.graalvm.compiler.core.common.spi.ForeignCallDescriptor.equals(ForeignCallDescriptor.java:85)
	parsing org.graalvm.collections.EconomicMapImpl.compareKeys(EconomicMapImpl.java:276)
	parsing org.graalvm.collections.EconomicMapImpl.findLinear(EconomicMapImpl.java:263)
	parsing org.graalvm.collections.EconomicMapImpl.find(EconomicMapImpl.java:256)
	parsing org.graalvm.collections.EconomicMapImpl.get(EconomicMapImpl.java:245)
	parsing com.oracle.svm.core.option.SubstrateOptionsParser.parseOption(SubstrateOptionsParser.java:201)
	parsing com.oracle.svm.core.option.RuntimeOptionParser.parseOptionAtRuntime(RuntimeOptionParser.java:186)
	parsing com.oracle.svm.core.option.RuntimeOptionParser.parse(RuntimeOptionParser.java:156)
	parsing com.oracle.svm.core.option.RuntimeOptionParser.parseAndConsumeAllOptions(RuntimeOptionParser.java:76)
	parsing com.oracle.svm.jni.functions.JNIInvocationInterface$Exports.JNI_CreateJavaVM(JNIInvocationInterface.java:151)
	parsing com.oracle.svm.core.code.IsolateEnterStub.JNIInvocationInterface$Exports_JNI_CreateJavaVM_8df18e9ea6eb1ed7b1c899a0bbc578da0e2cc0ad(generated:0)

	at com.oracle.graal.pointsto.util.AnalysisError.parsingError(AnalysisError.java:138)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.doParse(MethodTypeFlow.java:327)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureParsed(MethodTypeFlow.java:300)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.addContext(MethodTypeFlow.java:107)
	at com.oracle.graal.pointsto.DefaultAnalysisPolicy$DefaultVirtualInvokeTypeFlow.onObservedUpdate(DefaultAnalysisPolicy.java:191)
	at com.oracle.graal.pointsto.flow.TypeFlow.notifyObservers(TypeFlow.java:343)
	at com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:385)
	at com.oracle.graal.pointsto.BigBang$2.run(BigBang.java:511)
	at com.oracle.graal.pointsto.util.CompletionExecutor.lambda$execute$0(CompletionExecutor.java:171)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1426)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:177)
Caused by: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getNestHost() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
	at com.oracle.svm.hosted.substitute.AnnotationSubstitutionProcessor.lookup(AnnotationSubstitutionProcessor.java:183)
	at com.oracle.graal.pointsto.infrastructure.SubstitutionProcessor$ChainedSubstitutionProcessor.lookup(SubstitutionProcessor.java:128)
	at com.oracle.graal.pointsto.infrastructure.SubstitutionProcessor$ChainedSubstitutionProcessor.lookup(SubstitutionProcessor.java:128)
	at com.oracle.graal.pointsto.meta.AnalysisUniverse.lookupAllowUnresolved(AnalysisUniverse.java:397)
	at com.oracle.graal.pointsto.meta.AnalysisUniverse.lookup(AnalysisUniverse.java:377)
	at com.oracle.graal.pointsto.meta.AnalysisUniverse.lookup(AnalysisUniverse.java:75)
	at com.oracle.graal.pointsto.infrastructure.UniverseMetaAccess.lookupJavaMethod(UniverseMetaAccess.java:93)
	at com.oracle.graal.pointsto.meta.AnalysisMetaAccess.lookupJavaMethod(AnalysisMetaAccess.java:66)
	at com.oracle.graal.pointsto.meta.AnalysisMetaAccess.lookupJavaMethod(AnalysisMetaAccess.java:39)
	at com.oracle.svm.reflect.hosted.ReflectionSubstitutionType$ReflectiveInvokeMethod.buildGraph(ReflectionSubstitutionType.java:511)
	at com.oracle.graal.pointsto.meta.AnalysisMethod.buildGraph(AnalysisMethod.java:319)
	at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:185)
	at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:340)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.doParse(MethodTypeFlow.java:310)
	... 13 more
Error: Image build request failed with exit status 1
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:02 min
[INFO] Finished at: 2020-03-19T10:29:12+01:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal io.quarkus:quarkus-maven-plugin:1.2.1.Final:build (default) on project quarkus-hibernate-types-problem: Failed to build a runnable JAR: Failed to augment application classes: Build failure: Build failed due to errors
[ERROR] 	[error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Failed to build native image
[ERROR] 	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:319)
[ERROR] 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[ERROR] 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[ERROR] 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[ERROR] 	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
[ERROR] 	at io.quarkus.deployment.ExtensionLoader$2.execute(ExtensionLoader.java:915)
[ERROR] 	at io.quarkus.builder.BuildContext.run(BuildContext.java:279)
[ERROR] 	at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:2011)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1535)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1426)
[ERROR] 	at java.base/java.lang.Thread.run(Thread.java:834)
[ERROR] 	at org.jboss.threads.JBossThread.run(JBossThread.java:479)
[ERROR] Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
[ERROR] 	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:308)
[ERROR] 	... 12 more
[ERROR] -> [Help 1]
org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute goal io.quarkus:quarkus-maven-plugin:1.2.1.Final:build (default) on project quarkus-hibernate-types-problem: Failed to build a runnable JAR
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:215)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: org.apache.maven.plugin.MojoExecutionException: Failed to build a runnable JAR
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:194)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: io.quarkus.creator.AppCreatorException: Failed to augment application classes
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:188)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:53)
    at io.quarkus.creator.CuratedApplicationCreator.runTask (CuratedApplicationCreator.java:139)
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:178)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: io.quarkus.builder.BuildException: Build failure: Build failed due to errors
	[error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Failed to build native image
	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:319)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at io.quarkus.deployment.ExtensionLoader$2.execute(ExtensionLoader.java:915)
	at io.quarkus.builder.BuildContext.run(BuildContext.java:279)
	at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
	at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:2011)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1535)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1426)
	at java.base/java.lang.Thread.run(Thread.java:834)
	at org.jboss.threads.JBossThread.run(JBossThread.java:479)
Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:308)
	... 12 more

    at io.quarkus.builder.Execution.run (Execution.java:108)
    at io.quarkus.builder.BuildExecutionBuilder.execute (BuildExecutionBuilder.java:79)
    at io.quarkus.deployment.QuarkusAugmentor.run (QuarkusAugmentor.java:128)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:179)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:53)
    at io.quarkus.creator.CuratedApplicationCreator.runTask (CuratedApplicationCreator.java:139)
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:178)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: java.lang.RuntimeException: Failed to build native image
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build (NativeImageBuildStep.java:319)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at io.quarkus.deployment.ExtensionLoader$2.execute (ExtensionLoader.java:915)
    at io.quarkus.builder.BuildContext.run (BuildContext.java:279)
    at org.jboss.threads.ContextClassLoaderSavingRunnable.run (ContextClassLoaderSavingRunnable.java:35)
    at org.jboss.threads.EnhancedQueueExecutor.safeRun (EnhancedQueueExecutor.java:2011)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask (EnhancedQueueExecutor.java:1535)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run (EnhancedQueueExecutor.java:1426)
    at java.lang.Thread.run (Thread.java:834)
    at org.jboss.threads.JBossThread.run (JBossThread.java:479)
Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build (NativeImageBuildStep.java:308)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at io.quarkus.deployment.ExtensionLoader$2.execute (ExtensionLoader.java:915)
    at io.quarkus.builder.BuildContext.run (BuildContext.java:279)
    at org.jboss.threads.ContextClassLoaderSavingRunnable.run (ContextClassLoaderSavingRunnable.java:35)
    at org.jboss.threads.EnhancedQueueExecutor.safeRun (EnhancedQueueExecutor.java:2011)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask (EnhancedQueueExecutor.java:1535)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run (EnhancedQueueExecutor.java:1426)
    at java.lang.Thread.run (Thread.java:834)
    at org.jboss.threads.JBossThread.run (JBossThread.java:479)
[ERROR] 
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
```
    
</details>

### Second problem : build with GraalVM 19.3.1 and Java 8

I use sdkman to manage my different java sdk. 
In my terminal, I exported the following env variables.

```
export GRAALVM_HOME=$HOME/.sdkman/candidates/java/19.3.1.r8-grl
export JAVA_HOME=${GRAALVM_HOME}                               
export PATH=${GRAALVM_HOME}/bin:$PATH 
```

I modified `pom.xml` from

```
<maven.compiler.source>11</maven.compiler.source>
<maven.compiler.target>11</maven.compiler.target>
```

to

```
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
```

When I checked which version of Java will be used for the build

```
java -version

openjdk version "1.8.0_242"
OpenJDK Runtime Environment (build 1.8.0_242-b06)
OpenJDK 64-Bit GraalVM CE 19.3.1 (build 25.242-b06-jvmci-19.3-b07, mixed mode)
```

Then I launched the build with the following command.

```
./mvnw clean package -Pnative -DskipTests=true -e
```
 
It failed.

<details>
    <summary>Complete Trace</summary>
    
```
[INFO] Error stacktraces are turned on.
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< org.acme:quarkus-hibernate-types-problem >--------------
[INFO] Building quarkus-hibernate-types-problem 1.0.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ quarkus-hibernate-types-problem ---
[INFO] Deleting /home/jabberwock/git/github/quarkus-hibernate-types-problem/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ quarkus-hibernate-types-problem ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 3 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ quarkus-hibernate-types-problem ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 3 source files to /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ quarkus-hibernate-types-problem ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/jabberwock/git/github/quarkus-hibernate-types-problem/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ quarkus-hibernate-types-problem ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ quarkus-hibernate-types-problem ---
[INFO] Tests are skipped.
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ quarkus-hibernate-types-problem ---
[INFO] Building jar: /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT.jar
[INFO] 
[INFO] --- quarkus-maven-plugin:1.2.1.Final:build (default) @ quarkus-hibernate-types-problem ---
[INFO] [org.jboss.threads] JBoss Threads version 3.0.0.Final
[INFO] [org.hibernate.jpa.boot.internal.PersistenceXmlParser] HHH000318: Could not find any META-INF/persistence.xml file in the classpath
[INFO] [org.hibernate.Version] HHH000412: Hibernate Core {5.4.10.Final}
[INFO] [io.quarkus.deployment.pkg.steps.JarResultBuildStep] Building native image source jar: /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-native-image-source-jar/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Building native image from /home/jabberwock/git/github/quarkus-hibernate-types-problem/target/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-native-image-source-jar/quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Running Quarkus native-image plugin on GraalVM Version 19.3.1 CE
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] /home/jabberwock/.sdkman/candidates/java/19.3.1.r8-grl/bin/native-image -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-Dsun.nio.ch.maxUpdateArraySize=100 -J-DCoordinatorEnvironmentBean.transactionStatusManagerEnable=false -J-Dvertx.logger-delegate-factory-class-name=io.quarkus.vertx.core.runtime.VertxLogDelegateFactory -J-Dvertx.disableDnsResolver=true -J-Dio.netty.leakDetection.level=DISABLED -J-Dio.netty.allocator.maxOrder=1 --initialize-at-build-time= -H:InitialCollectionPolicy=com.oracle.svm.core.genscavenge.CollectionPolicy$BySpaceAndTime -jar quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner.jar -H:FallbackThreshold=0 -H:+ReportExceptionStackTraces -H:-AddAllCharsets -H:EnableURLProtocols=http,https --enable-all-security-services -H:NativeLinkerOption=-no-pie -H:+JNI --no-server -H:-UseServiceLoaderFeature -H:+StackTrace quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]    classlist:  12,570.82 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]        (cap):     739.80 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]        setup:   2,221.50 ms
10:15:30,910 INFO  [org.hib.Version] HHH000412: Hibernate Core {5.4.10.Final}
10:15:30,922 WARN  [Hibernate Types] You should use Hypersistence Optimizer to speed up your Hibernate application!
10:15:30,922 WARN  [Hibernate Types] For more details, go to https://vladmihalcea.com/hypersistence-optimizer/
10:15:30,922 INFO  [Hibernate Types] 
 _    _                           _     _
| |  | |                         (_)   | |
| |__| |_   _ _ __   ___ _ __ ___ _ ___| |_ ___ _ __   ___ ___
|  __  | | | | '_ \ / _ \ '__/ __| / __| __/ _ \ '_ \ / __/ _ \
| |  | | |_| | |_) |  __/ |  \__ \ \__ \ ||  __/ | | | (_|  __/
|_|  |_|\__, | .__/ \___|_|  |___/_|___/\__\___|_| |_|\___\___|
         __/ | |
        |___/|_|

           ____        _   _           _
          / __ \      | | (_)         (_)
         | |  | |_ __ | |_ _ _ __ ___  _ _______ _ __
         | |  | | '_ \| __| | '_ ` _ \| |_  / _ \ '__|
         | |__| | |_) | |_| | | | | | | |/ /  __/ |
          \____/| .__/ \__|_|_| |_| |_|_/___\___|_|
                | |
                |_|

10:15:30,922 INFO  [Hibernate Types] Check out the README page for more info about the Hypersistence Optimizer banner https://github.com/vladmihalcea/hibernate-types#how-to-remove-the-hypersistence-optimizer-banner-from-the-log
10:15:31,659 INFO  [org.hib.ann.com.Version] HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
10:15:31,683 INFO  [org.hib.dia.Dialect] HHH000400: Using dialect: io.quarkus.hibernate.orm.runtime.dialect.QuarkusPostgreSQL95Dialect
10:15:32,729 INFO  [org.jbo.threads] JBoss Threads version 3.0.0.Final
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]   (typeflow):  37,062.24 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]    (objects):  26,263.74 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]   (features):   1,178.21 ms
[quarkus-hibernate-types-problem-1.0.0-SNAPSHOT-runner:9109]     analysis:  68,899.15 ms
Error: Unsupported features in 3 methods
Detailed message:
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getConstantPool() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getConstantPool(System.java:1229)
Call path from entry point to java.lang.System$2.getConstantPool(Class): 
	at java.lang.System$2.getConstantPool(System.java:1229)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:323)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getExecutableTypeAnnotationBytes(Executable) is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
Call path from entry point to java.lang.System$2.getRawExecutableTypeAnnotations(Executable): 
	at java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:318)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getRawTypeAnnotations() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
Call path from entry point to java.lang.System$2.getRawClassTypeAnnotations(Class): 
	at java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:315)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)

com.oracle.svm.core.util.UserError$UserException: Unsupported features in 3 methods
Detailed message:
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getConstantPool() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getConstantPool(System.java:1229)
Call path from entry point to java.lang.System$2.getConstantPool(Class): 
	at java.lang.System$2.getConstantPool(System.java:1229)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:323)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getExecutableTypeAnnotationBytes(Executable) is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
Call path from entry point to java.lang.System$2.getRawExecutableTypeAnnotations(Executable): 
	at java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:318)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getRawTypeAnnotations() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
Call path from entry point to java.lang.System$2.getRawClassTypeAnnotations(Class): 
	at java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:315)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)

	at com.oracle.svm.core.util.UserError.abort(UserError.java:75)
	at com.oracle.svm.hosted.FallbackFeature.reportAsFallback(FallbackFeature.java:221)
	at com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:736)
	at com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:530)
	at com.oracle.svm.hosted.NativeImageGenerator.lambda$run$0(NativeImageGenerator.java:445)
	at java.util.concurrent.ForkJoinTask$AdaptedRunnableAction.exec(ForkJoinTask.java:1386)
	at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
	at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
	at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
	at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
Caused by: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Unsupported features in 3 methods
Detailed message:
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getConstantPool() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getConstantPool(System.java:1229)
Call path from entry point to java.lang.System$2.getConstantPool(Class): 
	at java.lang.System$2.getConstantPool(System.java:1229)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:323)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getExecutableTypeAnnotationBytes(Executable) is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
Call path from entry point to java.lang.System$2.getRawExecutableTypeAnnotations(Executable): 
	at java.lang.System$2.getRawExecutableTypeAnnotations(System.java:1247)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:318)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.Class.getRawTypeAnnotations() is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace: 
	at parsing java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
Call path from entry point to java.lang.System$2.getRawClassTypeAnnotations(Class): 
	at java.lang.System$2.getRawClassTypeAnnotations(System.java:1244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAllTypeAnnotations(TypeAnnotationParser.java:315)
	at sun.reflect.annotation.TypeAnnotationParser.fetchBounds(TypeAnnotationParser.java:299)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:244)
	at sun.reflect.annotation.TypeAnnotationParser.parseAnnotatedBounds(TypeAnnotationParser.java:237)
	at sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getAnnotatedBounds(TypeVariableImpl.java:241)
	at com.oracle.svm.reflect.TypeVariable_getAnnotatedBounds_88ea462da2b17ea45028db307519aaeeec9a4036_893.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:118)
	at com.sun.proxy.$Proxy281.equals(Unknown Source)
	at java.util.Hashtable.get(Hashtable.java:367)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.initializeLazyValue(SystemPropertiesSupport.java:183)
	at com.oracle.svm.core.jdk.SystemPropertiesSupport.getProperty(SystemPropertiesSupport.java:141)
	at com.oracle.svm.core.jdk.Target_java_lang_System.getProperty(JavaLangSubstitutions.java:342)
	at com.oracle.svm.jni.JNIJavaCallWrappers.jniInvoke_ARRAY:Ljava_lang_System_2_0002egetProperty_00028Ljava_lang_String_2_00029Ljava_lang_String_2(generated:0)

	at com.oracle.graal.pointsto.constraints.UnsupportedFeatures.report(UnsupportedFeatures.java:129)
	at com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:733)
	... 7 more
Error: Image build request failed with exit status 1
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:30 min
[INFO] Finished at: 2020-03-19T10:16:40+01:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal io.quarkus:quarkus-maven-plugin:1.2.1.Final:build (default) on project quarkus-hibernate-types-problem: Failed to build a runnable JAR: Failed to augment application classes: Build failure: Build failed due to errors
[ERROR] 	[error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Failed to build native image
[ERROR] 	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:319)
[ERROR] 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[ERROR] 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[ERROR] 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[ERROR] 	at java.lang.reflect.Method.invoke(Method.java:498)
[ERROR] 	at io.quarkus.deployment.ExtensionLoader$2.execute(ExtensionLoader.java:915)
[ERROR] 	at io.quarkus.builder.BuildContext.run(BuildContext.java:279)
[ERROR] 	at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:2011)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1535)
[ERROR] 	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1426)
[ERROR] 	at java.lang.Thread.run(Thread.java:748)
[ERROR] 	at org.jboss.threads.JBossThread.run(JBossThread.java:479)
[ERROR] Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
[ERROR] 	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:308)
[ERROR] 	... 12 more
[ERROR] -> [Help 1]
org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute goal io.quarkus:quarkus-maven-plugin:1.2.1.Final:build (default) on project quarkus-hibernate-types-problem: Failed to build a runnable JAR
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:215)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: org.apache.maven.plugin.MojoExecutionException: Failed to build a runnable JAR
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:194)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: io.quarkus.creator.AppCreatorException: Failed to augment application classes
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:188)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:53)
    at io.quarkus.creator.CuratedApplicationCreator.runTask (CuratedApplicationCreator.java:139)
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:178)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: io.quarkus.builder.BuildException: Build failure: Build failed due to errors
	[error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Failed to build native image
	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:319)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at io.quarkus.deployment.ExtensionLoader$2.execute(ExtensionLoader.java:915)
	at io.quarkus.builder.BuildContext.run(BuildContext.java:279)
	at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
	at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:2011)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1535)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1426)
	at java.lang.Thread.run(Thread.java:748)
	at org.jboss.threads.JBossThread.run(JBossThread.java:479)
Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
	at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:308)
	... 12 more

    at io.quarkus.builder.Execution.run (Execution.java:108)
    at io.quarkus.builder.BuildExecutionBuilder.execute (BuildExecutionBuilder.java:79)
    at io.quarkus.deployment.QuarkusAugmentor.run (QuarkusAugmentor.java:128)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:179)
    at io.quarkus.creator.phase.augment.AugmentTask.run (AugmentTask.java:53)
    at io.quarkus.creator.CuratedApplicationCreator.runTask (CuratedApplicationCreator.java:139)
    at io.quarkus.maven.BuildMojo.execute (BuildMojo.java:178)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:956)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:288)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:192)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at org.apache.maven.wrapper.BootstrapMainStarter.start (BootstrapMainStarter.java:39)
    at org.apache.maven.wrapper.WrapperExecutor.execute (WrapperExecutor.java:122)
    at org.apache.maven.wrapper.MavenWrapperMain.main (MavenWrapperMain.java:61)
Caused by: java.lang.RuntimeException: Failed to build native image
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build (NativeImageBuildStep.java:319)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at io.quarkus.deployment.ExtensionLoader$2.execute (ExtensionLoader.java:915)
    at io.quarkus.builder.BuildContext.run (BuildContext.java:279)
    at org.jboss.threads.ContextClassLoaderSavingRunnable.run (ContextClassLoaderSavingRunnable.java:35)
    at org.jboss.threads.EnhancedQueueExecutor.safeRun (EnhancedQueueExecutor.java:2011)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask (EnhancedQueueExecutor.java:1535)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run (EnhancedQueueExecutor.java:1426)
    at java.lang.Thread.run (Thread.java:748)
    at org.jboss.threads.JBossThread.run (JBossThread.java:479)
Caused by: java.lang.RuntimeException: Image generation failed. Exit code: 1
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build (NativeImageBuildStep.java:308)
    at sun.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:498)
    at io.quarkus.deployment.ExtensionLoader$2.execute (ExtensionLoader.java:915)
    at io.quarkus.builder.BuildContext.run (BuildContext.java:279)
    at org.jboss.threads.ContextClassLoaderSavingRunnable.run (ContextClassLoaderSavingRunnable.java:35)
    at org.jboss.threads.EnhancedQueueExecutor.safeRun (EnhancedQueueExecutor.java:2011)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask (EnhancedQueueExecutor.java:1535)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run (EnhancedQueueExecutor.java:1426)
    at java.lang.Thread.run (Thread.java:748)
    at org.jboss.threads.JBossThread.run (JBossThread.java:479)
[ERROR] 
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
```

</details>
