package mms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Highscore {

	private int highscore = 0;

	public Highscore() {
		File file = new File("save.xml");
		if (!file.exists()) {
			this.highscore = 0;
			System.out.println("sadas");
			save(file, 0);
		} else {
			this.highscore = load(new File("save.xml"));
		}
	}

	public void save(File saveFile, int highscore) {
		this.highscore = highscore;
		Document document = new Document();
		Element root = new Element("highscore");
		document.setRootElement(root);
		Element block = new Element("score");
		block.setAttribute("best_score", Integer.toString(highscore));
		root.addContent(block);
		XMLOutputter output = new XMLOutputter();
		try {
			output.output(document, new FileOutputStream(saveFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int load(File loadFile) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(loadFile);
			Element root = document.getRootElement();
			Element score = root.getChild("score");
			highscore = Integer.parseInt(score.getAttributeValue("best_score"));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return highscore;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscoreNew) {
		if (this.highscore < highscoreNew) {
			save(new File("save.xml"), highscoreNew);
		}
	}
}
