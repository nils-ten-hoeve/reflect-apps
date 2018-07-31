package nth.reflect.app.swdocgen.dom.page.web;

import java.util.List;

import nth.reflect.app.swdocgen.dom.documentation.GitHubWebInfo;
import nth.reflect.app.swdocgen.dom.page.ElementUtil;
import nth.reflect.fw.generic.util.StringUtil;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class PrintableWebPage extends WebPage {

	public PrintableWebPage(GitHubWebInfo info, Document javaDoc) {
		super(info.getGithubWebProjectLocation(), info.getClassName(), javaDoc);
	}

	@Override
	public Document createContents() {
		Document doc = createDocument(getTitle());
		
		createHead(doc);

		Element body = doc.body();

		createFrontCover(getJavaDoc(), body);

		createTableOfContents(getJavaDoc(), body);

		createContent(getJavaDoc(), body);

		return doc;

	}

	@Override
	protected String createFileName(String title) {
		StringBuilder fileName=new StringBuilder();
		fileName.append(StringUtil.convertToCamelCase(title, true));
		fileName.append("_Printable.html");
		return fileName.toString();
	}


	private static void createHead(Document doc) {
		Element head = doc.head();
		head.appendElement("meta").attr("charset", "utf-8");
		head.appendElement("meta")
				.attr("name", "viewport")
				.attr("content",
						"width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes");
	}

	private static Document createDocument(String title) {
		Document doc = new Document("");
		doc.appendChild(new DocumentType("html", "", "", ""));
		Element html = doc.appendElement("html");
		html.appendElement("head");
		html.appendElement("body");
		doc.title(title);
		return doc;
	}

	private static void createContent(Document javaDoc, Element body) {
		Element divContent = body.appendElement("div").attr("id", "content");
		Elements h1Elements = javaDoc.select("h1");
		for (Element h1 : h1Elements) {
			List<Node> chapterNodes = ElementUtil.findChapterNodes(h1);
			ElementUtil.addAllNodes(divContent, chapterNodes);
		}

	}

	private static void createFrontCover(Document javaDoc, Element body) {
		Element firstChapter = javaDoc.select("h1").first();
		List<Node> frontCoverElements = ElementUtil
				.findAllNodesBefore(firstChapter);
		Element divFrontCover = body.appendElement("div").attr("id",
				"frontCover");
		ElementUtil.addAllNodes(divFrontCover, frontCoverElements);
	}

	private static void createTableOfContents(Document javaDoc, Element body) {
		Element divToc = body.appendElement("div")
				.attr("id", "tableOfContents");
		divToc.appendElement("h1").html("Contents");
		Element ulh2 = null;
		Elements chaptersAndParagraph = javaDoc.select("h1,h2");
		for (Element chapterOrParagraph : chaptersAndParagraph) {
			if ("h1".equals(chapterOrParagraph.tagName())) {
				divToc.appendElement("h3").appendElement("a")
						.attr(HREF, "#" + chapterOrParagraph.id())
						.html(chapterOrParagraph.html());
				ulh2 = divToc.appendElement("ul");
			}
			if ("h2".equals(chapterOrParagraph.tagName())) {
				ulh2.appendElement("li").appendElement("a")
						.attr(HREF, "#" + chapterOrParagraph.id())
						.html(chapterOrParagraph.html());
			}
		}
	}

	@Override
	protected String createReference(Element hElement) {
		StringBuilder reference = new StringBuilder();
		reference.append("#");
		reference.append(hElement.id());
		return reference.toString();
	}

}
