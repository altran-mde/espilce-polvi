/*******************************************************************************
 * Copyright (C) 2022 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.espilce.polvi.xtext.generator

import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.Grammar
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.xtext.generator.Issues
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.xtext.generator.generator.GeneratorFragment2
import org.eclipse.xtext.xtext.generator.model.FileAccessFactory
import org.eclipse.xtext.xtext.generator.model.TypeReference
import org.eclipse.xtext.xtext.generator.xbase.XbaseUsageDetector
import org.espilce.polvi.emf.generator.api.AbstractGenerator
import org.espilce.polvi.emf.generator.api.fsa.IFileSystemAccess2
import org.espilce.polvi.emf.generator.xtext.PolviDelegateGenerator
import org.espilce.polvi.generator.api.context.IGeneratorContext

/**
 * Configures Xtext to integrate an Espilce Polvi generator, optionally generating a Polvi generator stub.
 */
class PolviGeneratorFragment extends GeneratorFragment2 {

  @Inject FileAccessFactory fileAccessFactory

  @Inject extension XtextGeneratorNaming
  @Inject extension XbaseUsageDetector

  @Accessors(PUBLIC_SETTER)
  String generatorClass

  override protected getGrammar() {
    super.getGrammar()
  }

  override checkConfiguration(Issues issues) {
    super.checkConfiguration(issues)

    if (generatorClass !== null) {
      try {
        val generatorClassURI = URI.createURI(generatorClass)
        if (generatorClassURI.scheme !== null && !generatorClassURI.scheme.equalsIgnoreCase('bundleclass')) {
          throw new IllegalArgumentException('Invalid scheme.')
        }
        if (generatorClassURI.segmentCount != 1) {
          throw new IllegalArgumentException('Expected exactly 1 segment.')
        }
        if (isGeneratePolviStub && generatorClassURI.authority !== null &&
          generatorClassURI.authority != projectConfig.eclipsePlugin.name) {
          issues.addError("Option 'generatorClass' is invalid. Cannot generate stub in other plug-in.", this)
        }
      } catch (IllegalArgumentException e) {
        issues.addError(
          "Option 'generatorClass' does not match format [bundleclass:/[/<bundle symbolic name>/]]<fully qualified class name>. " +
            e.message, this)
      }
    }
  }

  override boolean isGenerateStub() {
    super.isGenerateStub() || (!grammar.inheritsXbase && generatorClass !== null)
  }

  protected def boolean isGeneratePolviStub() {
    super.isGenerateStub()
  }

  override generate() {
    super.generate();

    if (isGenerateStub && projectConfig.runtime.manifest !== null) {
      projectConfig.runtime.manifest.requiredBundles += 'org.espilce.polvi.emf.generator.xtext'

      if (projectConfig.eclipsePlugin.name != grammar.polviGeneratorBundle) {
        projectConfig.runtime.manifest.requiredBundles += grammar.polviGeneratorBundle
      }
    }

    if (isGeneratePolviStub) {
      if (projectConfig.runtime.manifest !== null) {
        projectConfig.runtime.manifest.exportedPackages += language.grammar.polviGeneratorClass.packageName
      }

      if (generateXtendStub) {
        doGenerateXtendPolviStubFile
      } else {
        doGenerateJavaPolviStubFile
      }
    }
  }

  override TypeReference getGeneratorStub(Grammar grammar) {
    new TypeReference(grammar.runtimeBasePackage + '.generator.' + GrammarUtil.getSimpleName(grammar) +
      'PolviDelegateGenerator')
  }

  protected def TypeReference getPolviGeneratorClass(Grammar grammar) {
    if (generatorClass !== null) {
      val generatorClassURI = URI.createURI(generatorClass)
      return new TypeReference(generatorClassURI.lastSegment)
    }
    return new TypeReference(grammar.generatorStub.packageName + '.' + GrammarUtil.getSimpleName(grammar) +
      'PolviGenerator')
  }

  protected def String getPolviGeneratorBundle(Grammar grammar) {
    if (generatorClass !== null) {
      val generatorClassURI = URI.createURI(generatorClass)
      if (generatorClassURI.authority !== null) {
        return generatorClassURI.authority
      }
    }
    return projectConfig.eclipsePlugin.name
  }

  override protected doGenerateXtendStubFile() {
    val file = fileAccessFactory.createXtendFile(grammar.generatorStub, '''
      class «language.grammar.generatorStub.simpleName» extends «PolviDelegateGenerator» {
        new() {
          super(new «grammar.polviGeneratorClass.simpleName»())
        }
      }
    ''')
    file.importType(grammar.polviGeneratorClass)
    file.writeTo(projectConfig.runtime.srcGen)
  }

  def protected doGenerateXtendPolviStubFile() {
    fileAccessFactory.createXtendFile(grammar.polviGeneratorClass, '''
      /**
       * Generates code from your model files on save.
       * 
       * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
       */
      class «language.grammar.polviGeneratorClass.simpleName» extends «AbstractGenerator» {
      
        override void doGenerate(«Resource» resource, «IFileSystemAccess2» fsa, «IGeneratorContext» context) {
      //    fsa.generateFile('greetings.txt', 'People to greet: ' + 
      //      resource.allContents
      //        .filter(Greeting)
      //        .map[name]
      //        .join(', '))
        }
      }
    ''').writeTo(projectConfig.runtime.src)
  }

  override protected doGenerateJavaStubFile() {
    val file = fileAccessFactory.createJavaFile(grammar.generatorStub, '''
      public class «language.grammar.generatorStub.simpleName» extends «PolviDelegateGenerator» {
        public «language.grammar.generatorStub.simpleName»() {
          super(new «grammar.polviGeneratorClass.simpleName»());
        }
      }
    ''')
    file.importType(grammar.polviGeneratorClass)
    file.writeTo(projectConfig.runtime.srcGen)
  }

  def protected doGenerateJavaPolviStubFile() {
    fileAccessFactory.createJavaFile(grammar.polviGeneratorClass, '''
      /**
       * Generates code from your model files on save.
       * 
       * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
       */
      public class «language.grammar.polviGeneratorClass.simpleName» extends «AbstractGenerator» {
      
        @«Override»
        public void doGenerate(«Resource» resource, «IFileSystemAccess2» fsa, «IGeneratorContext» context) {
      //    Iterator<Greeting> filtered = Iterators.filter(resource.getAllContents(), Greeting.class);
      //    Iterator<String> names = Iterators.transform(filtered, new Function<Greeting, String>() {
      //
      //      @«Override»
      //      public String apply(Greeting greeting) {
      //        return greeting.getName();
      //      }
      //    });
      //    fsa.generateFile("greetings.txt", "People to greet: " + IteratorExtensions.join(names, ", "));
        }
      }
    ''').writeTo(projectConfig.runtime.src)
  }
}
