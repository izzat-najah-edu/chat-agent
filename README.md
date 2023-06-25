# Chat Agent

Chat Agent is a Spring Boot library designed to abstract the interaction with the OpenAI Chat Completion API. It is
particularly useful when working with various data formats and provides a streamlined way to format inputs and parse
outputs for the OpenAI API.

## Key Features

- **Data Formatting**: Inputs to the OpenAI API often need to be formatted from various formats (like plain strings,
  Markdown, etc.) to formats like JSON or XML. Chat Agent provides functionality to handle this formatting.

- **Data Parsing**: Similarly, outputs from the OpenAI API need to be parsed from formats like JSON or XML to actual
  Java objects. Chat Agent provides functionality to handle this parsing.

- **AgentService**: This is the core service of the library. It formats input data, performs chat completion, and parses
  the output.

- **Built-in Formatters and Parsers**: The library comes with built-in JSON formatters (for inputs) and parsers (for
  outputs). More can be added in future updates.

- **Custom Formatters and Parsers**: A key functionality is that you can implement any custom formatter or parser and
  use it with an AgentService.

- **Reactive Requests**: All requests are reactive. This is essential since chat completion requests can take a long
  time to process, and the reactive approach allows for better handling of these long-running requests.

## Example

Let's say you want to generate a blog article. You have a few keywords in mind, and you want a complete Markdown blog
article. You can achieve this by dividing the generation process into tasks:

1. **Outlining the Article:** The first task is to create an outline for the article. You can use an instance of
   `AgentService<'KeywordsString', 'JsonArticleOutline'>` for this. This service takes a few keywords and generates a
   JSON
   outline for the article. For example:

    ```
    {
      "title": "Multithreading in Java"
      "sections": [
          "Introduction",
          ...
          "conclusion"
      ]
    }
    ```

   In this example, the service has generated a title for the article and a list of section titles based on the provided
   keywords. Of course, for this to happen the prompt "template" used should ask chat completion to return JSON data.

2. **Writing the Sections:** Once you have the outline, the next task is to generate content for each section. You can
   use an `AgentService<'SectionTitleString', 'SectionContentMarkdown'>` for this. Which takes a section title and
   generates content for that section in Markdown format.
   For example, if you give it the section title "Introduction", it might generate:

    ```
   # Introduction
    Multithreading is a core concept in Java. It allows for concurrent execution of two or more parts of a program for
    maximum utilization of CPU...
   ```

3. **Assembling the Article:** Finally, once you have the content for all sections, you can arrange the sections in the
   order specified in the outline to create a complete blog article.

By dividing the process into these tasks and using the Chat Agent library to handle each task, you can generate a
complete blog article with just a few keywords. And this is an example.

## Getting Started

- To use the Chat Agent library, you need to add it as a dependency in your Spring Boot project. Once added, you can
  use `AgentServiceFactory` to create instances of `AgentService` with the appropriate formatter and parser, and use
  this service to interact with the OpenAI API.

- Note that you must have an environmental variable `OPENAI_API_KEY` with your Openai JWT.

- Also note that to use `AgentServiceFactory` bean in your spring application you should add @ComponentScan
  to your main application as follows:

    ```
  @SpringBootApplication
  @ComponentScan(basePackages = {"com.izzatalsharif.openai.chatagent"})
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
  ```