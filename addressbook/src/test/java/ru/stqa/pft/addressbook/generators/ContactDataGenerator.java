package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException e) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContact(count);
    switch (format) {
      case "xml" -> saveAsXml(contacts, new File(file));
      case "json" -> saveAsJson(contacts, new File(file));
      default -> System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    JsonSerializer<File> serializer = (src, typeOfSrc, context) -> new JsonPrimitive(src.getPath());
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(File.class, serializer)
            .setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);

    }
  }

  private List<ContactData> generateContact(int count) {
    List<ContactData> contacts = new ArrayList<>();
    File photo = new File("src/test/resources/rick.png");
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstName("Anastasia").withLastName("Dorogova").withNickname("Felix" + i)
              .withTitle(String.format("title %s", i)).withCompany(String.format("STest %s", i))
              .withAddress(String.format("test" + i + " " + "test" + i + " " + "test" + i))
              .withMobilePhone("89657845678" + i).withHomePhone("8495675692" + i).withWorkPhone(("8495777749" + i))
              .withEmail("test" + i + "@test.ru").withEmail2("test" + i + "@inetcom.ru").withEmail3("test" + i + "@wabadaba.com")
              .withBDay(String.format("1" + i)).withBMonth("October").withBYear(("199" + i))
              .withPhoto(new File(photo.toString())));//.withGroup("test " + i).
    }
    return contacts;
  }
}