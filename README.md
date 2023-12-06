# Chat Agent

Chat Agent is a Spring Boot library designed to abstract the interaction with the OpenAI Chat Completion API.

## 🔍 Problem

- Interacting with the OpenAI Chat Completion API often presents overhead due to specific data formatting and parsing
  requirements. Users might need to specify prompts in various standard formats like Markdown, XML, or JSON. Whether its
  input (request to the API) or an output (response message).

- This diversity in data formats often means multiple APIs are needed, requiring many dependencies in your project —one
  to connect with OpenAI, another to format input data, and yet another to parse the output data— which is inefficient.

- A much better approach is to combine all these data processes in one library.

## 💡 Solution

Chat Agent is a carefully designed & tested Spring Boot library. Offering several features:

- **Data Formatting**: It can handle many standard data formats such as JSON or XML optimally, Resulting in a simplified
  interaction with ChatGPT API.

- **Built-in Reactive (Async) Requests**: Chat Agent embodies reactivity in its operations. Recognizing that chat
  completion requests are often time-consuming. Reactive components are essential with GPT API. Hence, enhancing the
  performance and user experience through non-blocking, asynchronous processes.

- **Customizable Formatters and Parsers**: Chat Agent is designed to be flexible. It allows for the implementation of
  custom formatters and parsers, so you can use it for the standard data formats & customize it with your special data
  format while still keeping the number of library dependencies at minimum.

### Before:

- The Manual Approach - 3 APIs used.

![Diagram1](https://github.com/izzat5233/chat-agent/assets/92182269/0c4e3370-4081-4e2d-8a4a-bf207875abcf)

### After:

- Using Chat Agent API to handle all processes.

![Diagram2](https://github.com/izzat5233/chat-agent/assets/92182269/750b0b54-16de-4a00-a56f-d0b1d0eab8e3)

## 🛠️ In Details

- **Data Formatting**: Inputs to the OpenAI API often need to be formatted from various formats (like plain strings,
  Markdown, etc.) to formats like JSON or XML. Chat Agent provides functionality to handle this formatting.

- **Data Parsing**: Similarly, outputs from the OpenAI API need to be parsed from formats like JSON or XML to actual
  Java objects. Chat Agent provides functionality to handle this parsing.

- **AgentService**: This is the core service of the library. It formats input data, performs chat completion, and parses
  the output.

- **Built-in Formatters and Parsers**: The library comes with built-in JSON & XML formatters (for inputs)
  and parsers (for outputs). More might be added in future updates.

- **Custom Formatters and Parsers**: A key functionality is that you can implement any custom formatter or parser and
  use it with an AgentService.

- **Reactive Requests**: All requests are reactive. This is essential since chat completion requests can take a long
  time to process, and the reactive approach allows for better handling of these long-running requests.

## 📚 Example

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

## 🚀 Getting Started

- Import the dependency configuration as follows:

    ```
  @SpringBootApplication
  @Import(ChatAgentConfig.class)
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
  ```

- To use the Chat Agent library, you need to add it as a dependency in your Spring Boot project. Once added, you can
  use `AgentServiceFactory` to create instances of `AgentService` with the appropriate formatter and parser, and use
  this service to interact with the OpenAI API.

- If you want to understand how this API works. Check out the Integration Tests, especially `AgentServiceIT`.
  They provide a general idea of how everything works.

### Application Properties

- **`openai.api.key`** your OpenAI API Key. Must override it in your application.
  It's recommended to use `openai.api.key=${ENV_VARIABLE}` to protect your key from version control.

- **`openai.api.request-timeout`.** Chat completions typically take a long time to respond. So timeout is set to 5
  minutes by default. Override it if needed.

- **`openai.api.chat-completion-url`** set to `https://api.openai.com/v1/chat/completions`.
