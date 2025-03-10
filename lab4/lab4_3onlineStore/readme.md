## Are there any instances of “xpath”? 
- Yes, there are instances of xpath in the code but there are less stable if the page structure changes.
``` java
  WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
  By.xpath("/html/body/div/div[2]/div/div[2]/div/span[1]")));
```

## What about identifier-based locators? 
- Yes, there are instances of identifier-based locators in the code. They are more stable than xpath.
``` java
  WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
  By.id("bookTitle")));
```

## Which locator strategies are more robust?
- Identifier-based locators are more robust than xpath or CSS Seletores. They are less likely to break if the page structure changes.
- It's best to start wit Id or Class locators, best performance and better name to read.
- Tag name can be a dangerous way to locate elements. There are frequently multiple elements of the same tag present on the page. This is mostly useful when calling the findElements(By) method which returns a collection of elements.
- The recommendation is to keep your locators as compact and readable as possible. Asking WebDriver to traverse the DOM structure is an expensive operation, and the more you can narrow the scope of your search, the better.

