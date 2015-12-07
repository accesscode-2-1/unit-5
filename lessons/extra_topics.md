## Saturday

### Resources (all really good!)

* Dagger, QCon 2012 ([@swankjesse](https://github.com/swankjesse)): [video](http://www.infoq.com/presentations/Dagger)
* Dagger, Devoxx 2013 ([@jakewharton](https://github.com/jakewharton)): [video](https://www.parleys.com/tutorial/architecting-android-applications-dagger)
* Dagger 2, Devoxx 2014 ([@jakewharton](https://github.com/jakewharton)): [video](https://www.parleys.com/tutorial/5471cdd1e4b065ebcfa1d557/)
* Dagger 2, JavaSIG 2014 ([@gk5885](https://github.com/gk5885)): [video](https://www.youtube.com/watch?v=oK_XtfXPkqw)
* Talk on DI, Dagger 1 and Dagger 2 (thanks, [@AnthonyFermin](https://github.com/AnthonyFermin)!): [podcast](http://fragmentedpodcast.com/episodes/021/)

* Dagger, examples: [code](https://github.com/square/dagger/tree/master/examples)
* Dagger 2, examples: [code](https://github.com/google/dagger/tree/master/examples)

* Annotation Processing Boilerplate Destruction ([@jakewharton](https://github.com/jakewharton)): [[video](https://www.youtube.com/watch?v=dOcs-NKK-RA)] [[slides](https://speakerdeck.com/jakewharton/annotation-processing-boilerplate-destruction-square-waterloo-2014)]
 * Unfortunately, the [DroidCon NYC 2014 talk](https://www.youtube.com/watch?v=tRmJm2_qytM) is barely audible...

* a great [blog](http://securesoftwaredev.com/tag/reflection/) on annotation processing

* All of [Jake Wharton's slides](https://speakerdeck.com/jakewharton) on SpeakerDeck
* [u2020](https://github.com/JakeWharton/u2020), a sample app showcasing the Debug Drawer, Mock Mode and many great Android open source libraries


## Sunday

### Resources

* reflection: [tutorial](https://docs.oracle.com/javase/tutorial/reflect/index.html)

* cglib: [code](https://github.com/cglib/cglib)  (used 'under the covers' by Mockito)

* mockito: [blog] (https://corner.squareup.com/2012/10/mockito-android.html) (blog post)

 * Note: not too many great blogs on testing for Android...some contradict, others use outdated versions of libraries.


### Relevant Gradle Dependencies

JUnit
* junit:junit:4.12

Mockito
* org.mockito:mockito-core:1.10.19

AssertJ/Truth: Fluent Assertions
* org.assertj:assertj-core:3.2.0
* com.squareup.assertj:assertj-android:1.1.1
* com.google.truth:truth:0.27

Robolectric
* org.robolectric:robolectric:3.0

Espresso
* com.android.support.test.espresso:espresso-core:2.2
* com.android.support.test:runner:0.3
* com.android.support.test:rules:0.3
