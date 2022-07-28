/**
 * Copyright (C) 2022 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.espilce.polvi.xtext.generator;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xtext.generator.Issues;
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming;
import org.eclipse.xtext.xtext.generator.generator.GeneratorFragment2;
import org.eclipse.xtext.xtext.generator.model.FileAccessFactory;
import org.eclipse.xtext.xtext.generator.model.JavaFileAccess;
import org.eclipse.xtext.xtext.generator.model.ManifestAccess;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.model.XtendFileAccess;
import org.eclipse.xtext.xtext.generator.xbase.XbaseUsageDetector;
import org.espilce.polvi.emf.generator.api.AbstractGenerator;
import org.espilce.polvi.emf.generator.api.fsa.IFileSystemAccess2;
import org.espilce.polvi.emf.generator.xtext.PolviDelegateGenerator;
import org.espilce.polvi.generator.api.context.IGeneratorContext;

/**
 * Configures Xtext to integrate an Espilce Polvi generator, optionally generating a Polvi generator stub.
 */
@SuppressWarnings("all")
public class PolviGeneratorFragment extends GeneratorFragment2 {
  @Inject
  private FileAccessFactory fileAccessFactory;
  
  @Inject
  @Extension
  private XtextGeneratorNaming _xtextGeneratorNaming;
  
  @Inject
  @Extension
  private XbaseUsageDetector _xbaseUsageDetector;
  
  @Accessors(AccessorType.PUBLIC_SETTER)
  private String generatorClass;
  
  @Override
  protected Grammar getGrammar() {
    return super.getGrammar();
  }
  
  @Override
  public void checkConfiguration(final Issues issues) {
    super.checkConfiguration(issues);
    if ((this.generatorClass != null)) {
      try {
        final URI generatorClassURI = URI.createURI(this.generatorClass);
        if (((generatorClassURI.scheme() != null) && (!generatorClassURI.scheme().equalsIgnoreCase("bundleclass")))) {
          throw new IllegalArgumentException("Invalid scheme.");
        }
        int _segmentCount = generatorClassURI.segmentCount();
        boolean _notEquals = (_segmentCount != 1);
        if (_notEquals) {
          throw new IllegalArgumentException("Expected exactly 1 segment.");
        }
        if (((this.isGeneratePolviStub() && (generatorClassURI.authority() != null)) && 
          (!Objects.equal(generatorClassURI.authority(), this.getProjectConfig().getEclipsePlugin().getName())))) {
          issues.addError("Option \'generatorClass\' is invalid. Cannot generate stub in other plug-in.", this);
        }
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException e = (IllegalArgumentException)_t;
          String _message = e.getMessage();
          String _plus = ("Option \'generatorClass\' does not match format [bundleclass:/[/<bundle symbolic name>/]]<fully qualified class name>. " + _message);
          issues.addError(_plus, this);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }
  
  @Override
  public boolean isGenerateStub() {
    return (super.isGenerateStub() || ((!this._xbaseUsageDetector.inheritsXbase(this.getGrammar())) && (this.generatorClass != null)));
  }
  
  protected boolean isGeneratePolviStub() {
    return super.isGenerateStub();
  }
  
  @Override
  public void generate() {
    super.generate();
    if ((this.isGenerateStub() && (this.getProjectConfig().getRuntime().getManifest() != null))) {
      Set<String> _requiredBundles = this.getProjectConfig().getRuntime().getManifest().getRequiredBundles();
      _requiredBundles.add("org.espilce.polvi.emf.generator.xtext");
      String _name = this.getProjectConfig().getEclipsePlugin().getName();
      String _polviGeneratorBundle = this.getPolviGeneratorBundle(this.getGrammar());
      boolean _notEquals = (!Objects.equal(_name, _polviGeneratorBundle));
      if (_notEquals) {
        Set<String> _requiredBundles_1 = this.getProjectConfig().getRuntime().getManifest().getRequiredBundles();
        String _polviGeneratorBundle_1 = this.getPolviGeneratorBundle(this.getGrammar());
        _requiredBundles_1.add(_polviGeneratorBundle_1);
      }
    }
    boolean _isGeneratePolviStub = this.isGeneratePolviStub();
    if (_isGeneratePolviStub) {
      ManifestAccess _manifest = this.getProjectConfig().getRuntime().getManifest();
      boolean _tripleNotEquals = (_manifest != null);
      if (_tripleNotEquals) {
        Set<String> _exportedPackages = this.getProjectConfig().getRuntime().getManifest().getExportedPackages();
        String _packageName = this.getPolviGeneratorClass(this.getLanguage().getGrammar()).getPackageName();
        _exportedPackages.add(_packageName);
      }
      boolean _isGenerateXtendStub = this.isGenerateXtendStub();
      if (_isGenerateXtendStub) {
        this.doGenerateXtendPolviStubFile();
      } else {
        this.doGenerateJavaPolviStubFile();
      }
    }
  }
  
  @Override
  public TypeReference getGeneratorStub(final Grammar grammar) {
    String _runtimeBasePackage = this._xtextGeneratorNaming.getRuntimeBasePackage(grammar);
    String _plus = (_runtimeBasePackage + ".generator.");
    String _simpleName = GrammarUtil.getSimpleName(grammar);
    String _plus_1 = (_plus + _simpleName);
    String _plus_2 = (_plus_1 + 
      "PolviDelegateGenerator");
    return new TypeReference(_plus_2);
  }
  
  protected TypeReference getPolviGeneratorClass(final Grammar grammar) {
    if ((this.generatorClass != null)) {
      final URI generatorClassURI = URI.createURI(this.generatorClass);
      String _lastSegment = generatorClassURI.lastSegment();
      return new TypeReference(_lastSegment);
    }
    String _packageName = this.getGeneratorStub(grammar).getPackageName();
    String _plus = (_packageName + ".");
    String _simpleName = GrammarUtil.getSimpleName(grammar);
    String _plus_1 = (_plus + _simpleName);
    String _plus_2 = (_plus_1 + 
      "PolviGenerator");
    return new TypeReference(_plus_2);
  }
  
  protected String getPolviGeneratorBundle(final Grammar grammar) {
    if ((this.generatorClass != null)) {
      final URI generatorClassURI = URI.createURI(this.generatorClass);
      String _authority = generatorClassURI.authority();
      boolean _tripleNotEquals = (_authority != null);
      if (_tripleNotEquals) {
        return generatorClassURI.authority();
      }
    }
    return this.getProjectConfig().getEclipsePlugin().getName();
  }
  
  @Override
  protected void doGenerateXtendStubFile() {
    TypeReference _generatorStub = this.getGeneratorStub(this.getGrammar());
    StringConcatenationClient _client = new StringConcatenationClient() {
      @Override
      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
        _builder.append("class ");
        String _simpleName = PolviGeneratorFragment.this.getGeneratorStub(PolviGeneratorFragment.this.getLanguage().getGrammar()).getSimpleName();
        _builder.append(_simpleName);
        _builder.append(" extends ");
        _builder.append(PolviDelegateGenerator.class);
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("new() {");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("super(new ");
        String _simpleName_1 = PolviGeneratorFragment.this.getPolviGeneratorClass(PolviGeneratorFragment.this.getGrammar()).getSimpleName();
        _builder.append(_simpleName_1, "    ");
        _builder.append("())");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("}");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    };
    final XtendFileAccess file = this.fileAccessFactory.createXtendFile(_generatorStub, _client);
    file.importType(this.getPolviGeneratorClass(this.getGrammar()));
    file.writeTo(this.getProjectConfig().getRuntime().getSrcGen());
  }
  
  protected void doGenerateXtendPolviStubFile() {
    TypeReference _polviGeneratorClass = this.getPolviGeneratorClass(this.getGrammar());
    StringConcatenationClient _client = new StringConcatenationClient() {
      @Override
      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
        _builder.append("/**");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* Generates code from your model files on save.");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* ");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("class ");
        String _simpleName = PolviGeneratorFragment.this.getPolviGeneratorClass(PolviGeneratorFragment.this.getLanguage().getGrammar()).getSimpleName();
        _builder.append(_simpleName);
        _builder.append(" extends ");
        _builder.append(AbstractGenerator.class);
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("override void doGenerate(");
        _builder.append(Resource.class, "  ");
        _builder.append(" resource, ");
        _builder.append(IFileSystemAccess2.class, "  ");
        _builder.append(" fsa, ");
        _builder.append(IGeneratorContext.class, "  ");
        _builder.append(" context) {");
        _builder.newLineIfNotEmpty();
        _builder.append("//    fsa.generateFile(\'greetings.txt\', \'People to greet: \' + ");
        _builder.newLine();
        _builder.append("//      resource.allContents");
        _builder.newLine();
        _builder.append("//        .filter(Greeting)");
        _builder.newLine();
        _builder.append("//        .map[name]");
        _builder.newLine();
        _builder.append("//        .join(\', \'))");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("}");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    };
    this.fileAccessFactory.createXtendFile(_polviGeneratorClass, _client).writeTo(this.getProjectConfig().getRuntime().getSrc());
  }
  
  @Override
  protected void doGenerateJavaStubFile() {
    TypeReference _generatorStub = this.getGeneratorStub(this.getGrammar());
    StringConcatenationClient _client = new StringConcatenationClient() {
      @Override
      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
        _builder.append("public class ");
        String _simpleName = PolviGeneratorFragment.this.getGeneratorStub(PolviGeneratorFragment.this.getLanguage().getGrammar()).getSimpleName();
        _builder.append(_simpleName);
        _builder.append(" extends ");
        _builder.append(PolviDelegateGenerator.class);
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("public ");
        String _simpleName_1 = PolviGeneratorFragment.this.getGeneratorStub(PolviGeneratorFragment.this.getLanguage().getGrammar()).getSimpleName();
        _builder.append(_simpleName_1, "  ");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("super(new ");
        String _simpleName_2 = PolviGeneratorFragment.this.getPolviGeneratorClass(PolviGeneratorFragment.this.getGrammar()).getSimpleName();
        _builder.append(_simpleName_2, "    ");
        _builder.append("());");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("}");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    };
    final JavaFileAccess file = this.fileAccessFactory.createJavaFile(_generatorStub, _client);
    file.importType(this.getPolviGeneratorClass(this.getGrammar()));
    file.writeTo(this.getProjectConfig().getRuntime().getSrcGen());
  }
  
  protected void doGenerateJavaPolviStubFile() {
    TypeReference _polviGeneratorClass = this.getPolviGeneratorClass(this.getGrammar());
    StringConcatenationClient _client = new StringConcatenationClient() {
      @Override
      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
        _builder.append("/**");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* Generates code from your model files on save.");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* ");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("public class ");
        String _simpleName = PolviGeneratorFragment.this.getPolviGeneratorClass(PolviGeneratorFragment.this.getLanguage().getGrammar()).getSimpleName();
        _builder.append(_simpleName);
        _builder.append(" extends ");
        _builder.append(AbstractGenerator.class);
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("@");
        _builder.append(Override.class, "  ");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("public void doGenerate(");
        _builder.append(Resource.class, "  ");
        _builder.append(" resource, ");
        _builder.append(IFileSystemAccess2.class, "  ");
        _builder.append(" fsa, ");
        _builder.append(IGeneratorContext.class, "  ");
        _builder.append(" context) {");
        _builder.newLineIfNotEmpty();
        _builder.append("//    Iterator<Greeting> filtered = Iterators.filter(resource.getAllContents(), Greeting.class);");
        _builder.newLine();
        _builder.append("//    Iterator<String> names = Iterators.transform(filtered, new Function<Greeting, String>() {");
        _builder.newLine();
        _builder.append("//");
        _builder.newLine();
        _builder.append("//      @");
        _builder.append(Override.class);
        _builder.newLineIfNotEmpty();
        _builder.append("//      public String apply(Greeting greeting) {");
        _builder.newLine();
        _builder.append("//        return greeting.getName();");
        _builder.newLine();
        _builder.append("//      }");
        _builder.newLine();
        _builder.append("//    });");
        _builder.newLine();
        _builder.append("//    fsa.generateFile(\"greetings.txt\", \"People to greet: \" + IteratorExtensions.join(names, \", \"));");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("}");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    };
    this.fileAccessFactory.createJavaFile(_polviGeneratorClass, _client).writeTo(this.getProjectConfig().getRuntime().getSrc());
  }
  
  public void setGeneratorClass(final String generatorClass) {
    this.generatorClass = generatorClass;
  }
}
