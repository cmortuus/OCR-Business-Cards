# OCR Business Cards


# About
My methodology in doing this was to over engineer the code to a ridiculous degree. This turned out to be necessary since when running code with OCR you get garbled results and doing it in a simple way just to pass the tests I was given wouldn't have worked at all. Visually it is below par for how I like my code to look, but this is something I would be far more comfortable giving to a client than a normal implementation you would find on a dev test. 
 

# Rational
In order to do OCR in java it must be done through the command line. The libraries for java use a version of tesseract that is four years old and ocr has come a long way in that time. In order to get good results, it had to be done through command lines. I thought about executing a python script, but I was told I could use bash. I was not told I could use python. 

# Notes
OCR does not work 100% every time. You will see this in the test files I have given you. Many of the business cards online are not real, but templates so there is no similarity between the name of the individual and the start of the email so parsing the name is very unlikely to work, but if you have a large database of real business cards, I would love to see how it fairs. Mine is the first one, but the background I put on my card makes it really hard to do use for OCR.
 
# To Install
 
```
sudo apt install tesseract-ocr
```
```
sudo apt-get install mvn
```
If the above command fails run
```
sudo apt-get install maven
```
To compile, test, and run

```
cd OCRBusinessCards/
```

```
mvn clean install
```
```
cp bizCard* target/
```
```
cd target/
```
```
java -cp OCRBusinessCard-1.0.jar:dependency com.biz.cards.main 
```