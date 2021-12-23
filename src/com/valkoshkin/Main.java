package com.valkoshkin;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static DocumentBuilder documentBuilder;
    private static Transformer transformer;

    static {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (documentBuilder != null && transformer != null) {
            try {
                var sourceFilePath = args[0];
                var resultFilePath = args[1];

                var document = documentBuilder.parse(sourceFilePath);
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());

                var studentsList = document.getElementsByTagName("student");
                var studentElement = (Element) studentsList.item(0);
                var subjectsList = studentElement.getElementsByTagName("subject");
                var averageElement = (Element) studentElement.getElementsByTagName("average").item(0);

                var averageFromDocument = Double.parseDouble(averageElement.getTextContent());
                var correctAverage = getCorrectAverage(subjectsList);

                System.out.println("Correct average mark: " + correctAverage +
                        "\nAverage mark from XML file: " + averageFromDocument);

                if (averageFromDocument != correctAverage) {
                    averageElement.setTextContent(String.valueOf(correctAverage));
                    createDocument(studentsList, resultFilePath);
                    System.out.println("New XML document with correct average mark value has been created.");
                } else {
                    Files.deleteIfExists(Paths.get(resultFilePath));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("DocumentBuilder or Transformer is not initialized.");
        }
    }

    private static double getCorrectAverage(NodeList nodeList) {
        double sum = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            sum += Integer.parseInt(((Element) nodeList.item(i)).getAttribute("mark"));
        }
        return sum / nodeList.getLength();
    }

    private static void createDocument(NodeList nodeList, String filePath) throws TransformerException {
        var newDocument = documentBuilder.newDocument();
        var source = new DOMSource(newDocument);
        var streamResult = new StreamResult(filePath);

        for (int i = 0; i < nodeList.getLength(); i++) {
            newDocument.appendChild(newDocument.importNode(nodeList.item(i), true));
        }

        transformer.transform(source, streamResult);
    }
}
