package nth.reflect.app.swdocgen.dom.page.web;

import java.util.List;

import nth.reflect.app.swdocgen.dom.documentation.GitHubWebInfo;
import nth.reflect.app.swdocgen.dom.page.ElementUtil;
import nth.reflect.fw.generic.util.StringUtil;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class FancyWebPage extends WebPage {

	public FancyWebPage(GitHubWebInfo info, Document javaDoc) {
		super(info.getGithubWebProjectLocation(), info.getClassName(), javaDoc);
	}

	@Override
	public Document createContents() {
		// TODO wiki pages
		// TODO rename project to IntrospectDocumentationGenerator and add
		// IntrospectDocs to Maven resources
		// TODO check doc
		String title=getTitle();
		
		Document doc = createDocument(title);

		createHead(doc);

		Element body = doc.body();
		Element divPage = body.appendElement("div").attr("id", "page");

		createTitleBar(title, divPage);

		createContents(getJavaDoc(), divPage);

		createMenu(getJavaDoc(), divPage);

		return doc;
	}

	private Document createDocument(String title) {
		Document doc = new Document("");
		doc.appendChild(new DocumentType("html", "", "", ""));
		Element html = doc.appendElement("html");
		html.appendElement("head");
		html.appendElement("body");
		doc.title(title);
		return doc;
	}

	private void createTitleBar(String title, Element divPage) {
		Element divHeader = divPage.appendElement("div").attr("class",
				"header Fixed");
		divHeader.appendElement("a").attr("href", "#menu");
		divHeader.append(title);
	}

	private void createContents(Document javaDoc, Element divPage) {
		Element divContent = divPage.appendElement("div")
				.attr("class", "content").attr("id", "content");
		Elements h1Elements = javaDoc.select("h1");
		for (Element h1 : h1Elements) {
			List<Node> chapterNodes = ElementUtil.findChapterNodes(h1);
			ElementUtil.addAllNodes(divContent, chapterNodes);
		}
	}

	private void createHead(Document doc) {
		Element head = doc.head();
		head.appendElement("meta").attr("charset", "utf-8");
		head.appendElement("meta")
				.attr("name", "viewport")
				.attr("content",
						"width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes");

		head.appendElement("link").attr("type", "text/css")
				.attr("rel", "stylesheet").attr("href", "css/demo.css");
		head.appendElement("link").attr("type", "text/css")
				.attr("rel", "stylesheet")
				.attr("href", "dist/core/css/jquery.mmenu.all.css");
		head.appendElement("link").attr("type", "text/css")
				.attr("rel", "stylesheet")
				.attr("href", "dist/addons/css/jquery.mmenu.dragopen.css");

		head.appendElement("script").attr("type", "text/javascript")
				.attr("src", "http://hammerjs.github.io/dist/hammer.min.js");
		head.appendElement("script")
				.attr("type", "text/javascript")
				.attr("src",
						"http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js");
		head.appendElement("script").attr("type", "text/javascript")
				.attr("src", "dist/core/js/jquery.mmenu.min.js");
		head.appendElement("script").attr("type", "text/javascript")
				.attr("src", "dist/addons/js/jquery.mmenu.dragopen.min.js");
		head.appendElement("script")
				.attr("type", "text/javascript")
				.attr("src",
						"dist/addons/js/jquery.mmenu.fixedelements.min.js");
		head.appendElement("script")
				.attr("type", "text/javascript")
				.appendChild(
						new DataNode(
								getJavaScriptToEnsureTitleVisibilityForBookmarks(),
								""));
		head.appendElement("script").attr("type", "text/javascript")
				.appendChild(new DataNode(getJavaScriptToHandleMenu(), ""));
	}

	private void createMenu(Document javaDoc, Element divPage) {
		Element nav = divPage.appendElement("nav").attr("id", "menu");
		Element ulh1 = nav.appendElement("ul");
		Element ulh2 = null;
		Elements chaptersAndParagraph = javaDoc.select("h1,h2");
		for (Element chapterOrParagraph : chaptersAndParagraph) {
			if ("h1".equals(chapterOrParagraph.tagName())) {
				Element lih1 = ulh1.appendElement("li").appendElement("a")
						.attr("href", "#" + chapterOrParagraph.id())
						.html(chapterOrParagraph.html());
				ulh2 = lih1.appendElement("ul");
			}
			if ("h2".equals(chapterOrParagraph.tagName())) {
				ulh2.appendElement("li").appendElement("a")
						.attr("href", "#" + chapterOrParagraph.id())
						.html(chapterOrParagraph.html());
			}
		}
	}

//	private Element addChapterElements(Element chapterDiv, Element h1) {
//		Element parent = h1.parent();
//		if (parent == null) {
//			return chapterDiv;
//		}
//
//		Elements siblings = parent.children();
//		int startIndex = siblings.indexOf(h1) + 1;
//
//		chapterDiv.appendChild(h1);
//
//		for (int index = startIndex; index < siblings.size(); index++) {
//			Element sibling = siblings.get(index);
//			if ("h1".equals(sibling.tagName())) {
//				return chapterDiv;
//			} else {
//				chapterDiv.appendChild(sibling);
//			}
//		}
//		return chapterDiv;
//	}

	private String getJavaScriptToEnsureTitleVisibilityForBookmarks() {
		StringBuilder js = new StringBuilder();
		// see: https://github.com/twbs/bootstrap/issues/1768
		js.append("var shiftWindow = function() { scrollBy(0, -50) };");
		js.append("if (location.hash) shiftWindow();");
		js.append("window.addEventListener('hashchange', shiftWindow);");
		return js.toString();
	}

	private String getJavaScriptToHandleMenu() {
		StringBuilder js = new StringBuilder();

		js.append("$(function() {");
		js.append("	var $menu = $('nav#menu'),");
		js.append("		$html = $('html, body');");
		js.append("");
		js.append("	$menu.mmenu({");
		js.append("		dragOpen: true");
		js.append("	});");
		js.append("");
		js.append("	var $anchor = false;");
		js.append("	$menu.find( 'li > a' ).on(");
		js.append("		'click',");
		js.append("		function( e )");
		js.append("		{");
		js.append("			$anchor = $(this);");
		js.append("		}");
		js.append("	);");
		js.append("");
		js.append("	var api = $menu.data( 'mmenu' );");
		js.append("	api.bind( 'closed',");
		js.append("		function()");
		js.append("		{");
		js.append("			if ( $anchor )");
		js.append("			{");
		js.append("				var href = $anchor.attr( 'href' );");
		js.append("				$anchor = false;");
		js.append("");
		js.append("				if ( href.slice( 0, 1 ) == '#' )");
		js.append("				{");
		js.append("					$html.animate({");
		js.append("						scrollTop: $( href ).offset().top -50");
		js.append("					});	");
		js.append("				}");
		js.append("			}");
		js.append("		}");
		js.append("	);");
		js.append("});");

//		js.append("$(function() {");
//		js.append("$('nav#menu').mmenu({");
//		js.append("	extensions	: [ 'effect-slide-menu', 'pageshadow' ],");
//		js.append("	searchfield	: true,");
//		js.append("	counters	: true,");
//		js.append("	navbar 		: {");
//		js.append("		MaterialAppBarTitle		: 'Advanced menu'");
//		js.append("	},");
//		js.append("	navbars		: [");
//		js.append("		{");
//		js.append("			position	: 'top',");
//		js.append("			content		: [ 'searchfield' ]");
//		js.append("		}, {");
//		js.append("			position	: 'top',");
//		js.append("			content		: [");
//		js.append("				'prev',");
//		js.append("				'MaterialAppBarTitle',");
//		js.append("				'close'");
//		js.append("			]");
//		js.append("		}, {");
//		js.append("			position	: 'bottom',");
//		js.append("			content		: [");
//		js.append("				'<a href='http://mmenu.frebsite.nl/wordpress-plugin.html' target='_blank'>WordPress plugin</a>'");
//			js.append("			]");
//		js.append("		}");
//		js.append("	]");
//		js.append("});");
//		js.append("});");
		
		
//		js.append("	var api = $menu.data( 'mmenu' );");
//		js.append("	api.bind( 'closed',");
//		js.append("		function()");
//		js.append("		{");
//		js.append("			if ( $anchor )");
//		js.append("			{");
//		js.append("				var href = $anchor.attr( 'href' );");
//		js.append("				$anchor = false;");
//		js.append("");
//		js.append("				if ( href.slice( 0, 1 ) == '#' )");
//		js.append("				{");
//		js.append("					$html.animate({");
//		js.append("						scrollTop: $( href ).offset().top -50");
//		js.append("					});	");
//		js.append("				}");
//		js.append("			}");
//		js.append("		}");
//		js.append("	);");
//		js.append("});");

		
		return js.toString();
	}

	@Override
	protected String createFileName(String title) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(StringUtil.convertToCamelCase(title, true));
		fileName.append(".html");
		return fileName.toString();
	}

	@Override
	protected String createReference(Element hElement) {
		StringBuilder reference = new StringBuilder();
		reference.append("#");
		reference.append(hElement.id());
		return reference.toString();
		// TODO add reference to page when splitting up in multiple pages
	}

}
