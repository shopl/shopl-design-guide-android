package com.shopl.sdg.mcp;

import com.google.gson.GsonBuilder;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.*;

/** Extracts syntax facts from a pinned source snapshot. It does not claim type resolution. */
public final class ExtractCatalog {
    private static final List<Object> components = new ArrayList<>();
    private static final List<Object> types = new ArrayList<>();
    private static final List<Object> examples = new ArrayList<>();
    private static final List<Object> assets = new ArrayList<>();
    private static final List<Object> excluded = new ArrayList<>();
    private static Path root;
    private static String sha;

    private static Map<String, Object> map(Object... values) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) result.put((String) values[i], values[i + 1]);
        return result;
    }
    private static String text(PsiElement element) { return element == null ? null : element.getText(); }
    private static String hash(String value) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8))).substring(0, 16);
    }
    private static Map<String, Object> source(Path path, PsiElement element) throws Exception {
        int line = element == null ? 1 : (int) element.getContainingFile().getText()
                .substring(0, element.getTextOffset()).chars().filter(c -> c == '\n').count() + 1;
        String relative = root.relativize(path).toString().replace('\\', '/');
        return map("repository", "https://github.com/shopl/shopl-design-guide-android", "sha", sha,
                "path", relative, "line", line, "kind", "source_fact");
    }
    private static boolean visible(KtModifierListOwner d) {
        return !d.hasModifier(KtTokens.PRIVATE_KEYWORD) && !d.hasModifier(KtTokens.INTERNAL_KEYWORD)
                && !d.hasModifier(KtTokens.PROTECTED_KEYWORD);
    }
    private static List<String> annotations(KtAnnotated d) {
        return d.getAnnotationEntries().stream().map(PsiElement::getText).toList();
    }
    private static boolean annotated(KtAnnotated d, String name) {
        return d.getAnnotationEntries().stream().anyMatch(a -> a.getShortName() != null
                && a.getShortName().asString().equals(name));
    }
    private static String doc(KtDeclaration d) { return text(d.getDocComment()); }
    private static String signature(KtNamedFunction fn) {
        var body = fn.getBodyExpression();
        String raw = body == null ? fn.getText() : fn.getText().substring(0,
                body.getTextRange().getStartOffset() - fn.getTextRange().getStartOffset()).stripTrailing().replaceAll("=\\s*$", "").stripTrailing();
        if (fn.getDocComment() != null) raw = raw.replace(fn.getDocComment().getText(), "");
        return raw.strip();
    }
    private static List<String> references(KtTypeReference type) {
        if (type == null) return List.of();
        return PsiTreeUtil.findChildrenOfType(type, KtUserType.class).stream()
                .filter(t -> !(t.getParent() instanceof KtUserType))
                .map(t -> {
                    List<String> names = new ArrayList<>();
                    for (KtUserType q = t; q != null; q = q.getQualifier()) names.add(0, q.getReferencedName());
                    return String.join(".", names);
                }).distinct().toList();
    }
    private static void function(Path path, KtFile file, KtNamedFunction fn, boolean sample) throws Exception {
        String name = fn.getName();
        if (name == null) return;
        List<String> calls = PsiTreeUtil.findChildrenOfType(fn, KtCallExpression.class).stream()
                .map(c -> text(c.getCalleeExpression())).filter(Objects::nonNull)
                .filter(n -> n.startsWith("SDG")).distinct().sorted().toList();
        String id = file.getPackageFqName().asString() + "." + name;
        String sig = signature(fn);
        var imports = file.getImportDirectives().stream().map(PsiElement::getText).toList();
        if (sample || annotated(fn, "Preview")) {
            if (!calls.isEmpty()) examples.add(map("id", "example:" + id + ":" + hash(root.relativize(path) + ":" + sig), "name", name,
                    "code", fn.getText(), "imports", imports, "calls", calls,
                    "packageName", file.getPackageFqName().asString(),
                    "callSites", PsiTreeUtil.findChildrenOfType(fn, KtCallExpression.class).stream()
                        .filter(c -> c.getCalleeExpression() != null && c.getCalleeExpression().getText().startsWith("SDG"))
                        .map(c -> map("name", c.getCalleeExpression().getText(),
                            "argumentCount", c.getValueArguments().size(),
                            "namedArguments", c.getValueArguments().stream().filter(a -> a.isNamed()).map(a -> a.getArgumentName().getAsName().asString()).toList(),
                            "trailingLambda", !c.getLambdaArguments().isEmpty())).toList(),
                    "origin", "source_sample",
                    "verification", "source_only", "requiredContext", List.of("원본 파일의 보조 선언과 리소스가 필요할 수 있습니다. source 경로를 확인하세요."),
                    "adaptationPoints", List.of("샘플의 문구·데이터·상태를 제품의 리소스와 상태·이벤트에 연결하세요."),
                    "source", source(path, fn)));
            return;
        }
        if (!visible(fn) || !annotated(fn, "Composable")) {
            if (annotated(fn, "Composable")) excluded.add(map("name", id, "reason", "non_public", "source", source(path, fn)));
            return;
        }
        List<Object> params = new ArrayList<>();
        for (KtParameter p : fn.getValueParameters()) params.add(map("name", p.getName(),
                "type", text(p.getTypeReference()), "required", !p.hasDefaultValue() && !p.isVarArg(),
                "defaultValue", text(p.getDefaultValue()), "vararg", p.isVarArg(), "annotations", annotations(p)));
        List<String> checks = PsiTreeUtil.findChildrenOfType(fn, KtCallExpression.class).stream()
                .filter(c -> c.getCalleeExpression() != null && Set.of("require", "check", "requireNotNull", "checkNotNull").contains(text(c.getCalleeExpression())))
                .map(PsiElement::getText).toList();
        components.add(map("id", id, "overloadId", hash(sig), "name", name,
                "receiver", text(fn.getReceiverTypeReference()), "signature", sig,
                "import", "import " + id, "sourceImports", imports, "parameters", params,
                "referencedTypes", java.util.stream.Stream.concat(java.util.stream.Stream.concat(
                    references(fn.getReceiverTypeReference()).stream(), references(fn.getTypeReference()).stream()),
                    fn.getValueParameters().stream().flatMap(p -> references(p.getTypeReference()).stream())).distinct().toList(),
                "returnType", text(fn.getTypeReference()), "annotations", annotations(fn),
                "documentation", doc(fn), "constraintExpressions", checks,
                "deprecated", annotated(fn, "Deprecated"), "source", source(path, fn)));
    }
    private static void declaration(Path path, KtFile file, KtDeclaration d, String owner, boolean sample) throws Exception {
        if (d instanceof KtNamedFunction fn) {
            if (owner.isEmpty()) function(path, file, fn, sample);
            else if (annotated(fn, "Composable")) excluded.add(map("name", owner + "." + fn.getName(),
                    "reason", "member_composable_not_indexed", "source", source(path, fn)));
            return;
        }
        if (sample || !(d instanceof KtNamedDeclaration named) || !visible(named)) return;
        String name = named.getName();
        if (name == null) return;
        String qualified = owner.isEmpty() ? file.getPackageFqName() + "." + name : owner + "." + name;
        String relative = root.relativize(path).toString();
        if (d instanceof KtClassOrObject klass) {
            if (!(d instanceof KtEnumEntry)) types.add(map("id", qualified, "name", name,
                    "declaration", klass.getText(), "sourceImports", file.getImportDirectives().stream().map(PsiElement::getText).toList(),
                    "source", source(path, klass)));
            if (owner.endsWith(".SDGTypography") && d instanceof KtObjectDeclaration) {
                assets.add(map("id", qualified, "name", name, "kind", "typography",
                        "reference", "SDGTypography." + name, "import", "import " + owner,
                        "valueExpression", klass.getSuperTypeListEntries().stream().map(PsiElement::getText).toList(),
                        "documentation", doc(klass), "source", source(path, klass)));
            }
            for (KtDeclaration child : klass.getDeclarations()) declaration(path, file, child, qualified, false);
        } else if (d instanceof KtTypeAlias alias) {
            types.add(map("id", qualified, "name", name, "declaration", alias.getText(), "source", source(path, alias)));
        } else if (d instanceof KtProperty p && relative.contains("/foundation/") && !owner.isEmpty()) {
            String kind = owner.endsWith(".SDGColor") ? "color" : owner.endsWith(".SDGSpacing") ? "spacing"
                    : (owner.endsWith(".SDGCornerRadius") || owner.contains(".SDGCornerRadius.")) ? "radius" : null;
            if (kind != null) assets.add(map("id", qualified, "name", name, "kind", kind,
                    "reference", owner.substring(owner.lastIndexOf('.') + 1) + "." + name,
                    "import", "import " + owner, "type", text(p.getTypeReference()),
                    "valueExpression", p.getInitializer() == null ? text(p.getGetter()) : text(p.getInitializer()),
                    "documentation", doc(p), "source", source(path, p)));
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length < 4 || args.length > 5) throw new IllegalArgumentException("sourceRoot sourceSha version outputJson [curatedSourceRoot] required");
        root = Path.of(args[0]).toAbsolutePath(); sha = args[1];
        var disposable = Disposer.newDisposable();
        try {
            var env = KotlinCoreEnvironment.createForProduction(disposable, new CompilerConfiguration(), EnvironmentConfigFiles.JVM_CONFIG_FILES);
            var factory = new KtPsiFactory(env.getProject(), false);
            int files = 0;
            for (String module : List.of("sdg", "sdg-common", "app")) {
                Path src = root.resolve(module + "/src/main/java");
                try (var paths = Files.walk(src)) {
                    for (Path path : paths.filter(p -> p.toString().endsWith(".kt")).sorted().toList()) {
                        KtFile file = factory.createFile(path.getFileName().toString(), Files.readString(path));
                        var errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement.class);
                        if (!errors.isEmpty()) throw new IllegalStateException("Parse errors: " + path + " " + errors.iterator().next().getErrorDescription());
                        files++;
                        for (KtDeclaration d : file.getDeclarations()) declaration(path, file, d, "", module.equals("app"));
                    }
                }
            }
            Path res = root.resolve("sdg-resource/src/main/res");
            try (var paths = Files.walk(res)) {
                for (Path path : paths.filter(Files::isRegularFile).sorted().toList()) {
                    if (!path.getParent().getFileName().toString().startsWith("drawable")) continue;
                    String filename = path.getFileName().toString();
                    String name = filename.replaceFirst("(?:\\.9)?\\.[^.]+$", "");
                    String id = "com.shopl.sdg_resource.R.drawable." + name;
                    assets.add(map("id", id + ":" + path.getParent().getFileName(), "name", name, "kind", "icon",
                            "reference", "R.drawable." + name, "import", "import com.shopl.sdg_resource.R",
                            "qualifier", path.getParent().getFileName().toString(), "source", source(path, null)));
                }
            }
            List<Object> curated = new ArrayList<>();
            if (args.length == 5) {
                Path curatedRoot = Path.of(args[4]).toAbsolutePath();
                try (var paths = Files.walk(curatedRoot)) {
                    for (Path path : paths.filter(p -> p.toString().endsWith(".kt")).sorted().toList()) {
                        String code = Files.readString(path);
                        KtFile file = factory.createFile(path.getFileName().toString(), code);
                        if (!PsiTreeUtil.findChildrenOfType(file, PsiErrorElement.class).isEmpty()) throw new IllegalStateException("Curated parse error: " + path);
                        for (KtDeclaration d : file.getDeclarations()) {
                            if (!(d instanceof KtNamedFunction fn) || !annotated(fn, "Composable") || !visible(fn)) continue;
                            int before = examples.size();
                            function(path, file, fn, true);
                            if (examples.size() == before) continue;
                            @SuppressWarnings("unchecked")
                            var item = (Map<String, Object>) examples.remove(examples.size() - 1);
                            item.put("id", "curated:" + fn.getName());
                            item.put("origin", "curated");
                            item.put("code", code);
                            item.put("requiredContext", List.of("전체 Kotlin 파일입니다. 상태·이벤트·리소스에서 가져온 문구는 호출 화면이 제공합니다."));
                            item.put("source", map("repository", "local:sdg-android-mcp", "sha", "sha256:" + HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(code.getBytes(StandardCharsets.UTF_8))),
                                "path", "verification/src/main/kotlin/" + curatedRoot.relativize(path), "line", 1, "kind", "curated_example"));
                            curated.add(item);
                        }
                    }
                }
            }
            var result = map("schemaVersion", 1, "version", args[2], "sourceSha", sha,
                    "components", components, "types", types, "examples", examples, "assets", assets, "curatedExamples", curated,
                    "coverage", map("kotlinFiles", files, "overloads", components.size(), "types", types.size(),
                            "examples", examples.size(), "assets", assets.size(), "excluded", excluded,
                            "typeResolution", "syntax_only", "exampleVerification", "source_only"));
            Files.writeString(Path.of(args[3]), new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create().toJson(result) + "\n");
            System.out.println("Extracted " + components.size() + " overloads, " + assets.size() + " assets, " + examples.size() + " source examples");
        } finally { Disposer.dispose(disposable); }
    }
}
