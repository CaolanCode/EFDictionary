import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

class EFDictionaryUse 
    {
        public static void main(String [] args)
            {
                options();
            }

        public static void options()
            {
                EFDictionary dictionary[];
                EFDictionary tempObject;
                String english;
                String french;
                int option;
                int exit;
                boolean wordCheck;
                int first;
                int noOfPairs;
                int position;
                
                option = 0;
                exit = 4;
                noOfPairs = 0;
                first = 1;

                Scanner scan = new Scanner(System.in);
                dictionary = new EFDictionary[first];
                while(option != exit)
                    {
                        menu();
                        System.out.println("Enter an option 1 - 5:");
                        option = scan.nextInt();

                        switch(option)  
                            {
                                case 1:
                                    {
                                        System.out.println("Enter an English word that you would like to enter to the EFDictionary:");
                                        english = scan.next();
                                        wordCheck = checkEWord(english, dictionary, noOfPairs);
                                        if(!wordCheck)
                                            {
                                                System.out.println("Enter the French equivalant:");
                                                french = scan.next();
                                                tempObject = new EFDictionary(english, french);
                                                position = findPlace(noOfPairs, dictionary, english);
                                                dictionary = addOne(dictionary, noOfPairs, position, tempObject);
                                                System.out.println(dictionary[position].toString());
                                                noOfPairs++;
                                            }
                                        else
                                            {
                                                System.out.println("Sorry the word " + english + " already exists");
                                            }
                                            break;
                                    }

                                case 2:
                                    {
                                        System.out.println("Enter an French word that you would like to enter to the EFDictionary:");
                                        french = scan.next();
                                        wordCheck = checkFWord(french, dictionary, noOfPairs);
                                        if(!wordCheck)
                                            {
                                                System.out.println("Enter the English equivalant:");
                                                english = scan.next();
                                                tempObject = new EFDictionary(english, french);
                                                position = findPlace(noOfPairs, dictionary, english);
                                                dictionary = addOne(dictionary, noOfPairs, position, tempObject);
                                                System.out.println(dictionary[position].toString());
                                                noOfPairs++;
                                            }
                                        else
                                            {
                                                System.out.println("Sorry the word " + french + " already exists");
                                            }
                                            break;
                                    }

                                case 3:
                                    {
                                        System.out.println("Enter the English word of the pair that you would like to delete");
                                        english = scan.next();
                                        wordCheck = checkEWord(english, dictionary, noOfPairs);
                                        if(wordCheck)
                                            {
                                                dictionary = deleteOne(dictionary, english);
                                                noOfPairs--;
                                            }
                                        else  
                                            {
                                                System.out.println(english + " is not in the EFDictionary");
                                            }
                                            break;
                                    }

                                case 4:
                                    {
                                        writerFile(dictionary);                             }
                                        exit();
                                    }
                            }
                            scan.close();
            }
                 

        public static void menu()
            {
                System.out.println("Menu:");
                System.out.println();
                System.out.println("1. Add English Word to Dictionary:");
                System.out.println("2. Add French Word to Dictionary:");
                System.out.println("3. Delete a word from the menu:");
                System.out.println("4. Exit:");
            }

        public static boolean checkEWord(String theEnglish, EFDictionary dictionary[],int numOfElements)
            {
                int step;
                boolean found;

                found = false;
                step = 0;

                while(step < numOfElements && !found)
                    {
                        if(theEnglish.equals(dictionary[step].getEWord()))
                            {
                                found = true;
                            }
                        else    
                            {
                                step++;
                            }
                    }

                return found;
            }

        public static boolean checkFWord(String theFrench, EFDictionary dictionary[],int numOfElements)
            {
                int step;
                boolean found;

                found = false;
                step = 0;

                while(step < numOfElements && !found)
                    {
                        if(theFrench.equals(dictionary[step].getFWord()))
                            {
                                found = true;
                            }
                        else    
                            {
                                step++;
                            }
                    }

                return found;
            }

        public static int findPlace(int numOfWords, EFDictionary dictionary[], String theEnglish)
            {
                int step;
                int place;

                step = 0;
                while((step < numOfWords) && (theEnglish.compareTo(dictionary[step].getEWord())>0))
                    {
                        step++;
                    }
                place = step;
                return place;
            }

        public static EFDictionary[] addOne(EFDictionary[] theDictionary, int theNumOfWords, int thePlace, EFDictionary theTempObject)
            {
                int step;

                if(theNumOfWords == 0)
                    {
                        theDictionary[0] = theTempObject;
                    }
                else    
                    {
                        theDictionary = Arrays.copyOf(theDictionary, theDictionary.length+1);
                        for(step = theNumOfWords-1; step >= thePlace; step--)
                            {
                                theDictionary[step+1] = theDictionary[step];
                            }
                        theDictionary[thePlace] = theTempObject;
                    }
                    return theDictionary;
            }

        public static EFDictionary[] deleteOne(EFDictionary theDictionary[], String theWord)
            {
                int index;
                int nullCounter;
                int nullIndex;

                nullCounter = 0;
                nullIndex = 0;

                for(index = 0; index < theDictionary.length; index++)
                    {
                        if(theWord.equals(theDictionary[index].getEWord()))
                            {
                                theDictionary[index] = null;
                                nullCounter++;
                            }
                    }

                EFDictionary[] newDictionary = new EFDictionary[theDictionary.length - nullCounter];
                for(index = 0; index < theDictionary.length; index++)
                    {
                        if(theDictionary[index] != null)
                            {
                                newDictionary[nullIndex] = theDictionary[index];
                                nullIndex++;
                            }
                    }

                return newDictionary;
            }

        public static void writerFile(EFDictionary[] dictionary)
            {
                int index;
                PrintStream writer;
                index = 0;

                File file = new File("EFDictionary.txt");
                
                try 
                    {
                        writer = new PrintStream(file);
                        while(index < dictionary.length)
                            {
                                writer.println(dictionary[index].getEWord());
                                writer.println(dictionary[index].getFWord());
                                index++;
                            }
                    writer.close();
                    } 
                catch (FileNotFoundException e) 
                    {
                        e.printStackTrace();
                    }
                
            }

        public static void exit()
            {
                System.exit(0);
            }
    }
