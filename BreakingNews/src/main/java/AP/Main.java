package AP;

import java.util.Scanner;


public class Main
{
    public static void main(String[] args) {

        Infrastructure infrastructure = new Infrastructure("5dbb9d5f7cd24d1bab632f2f93e02664");

        //get and set information
        infrastructure.parseInformation();

        //display menu
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("TOP NEWS!");
            //Display News
            infrastructure.displayNewsList();

            System.out.print("\nWhich One Do You Want? ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("EXITing ... ");
                break;
            } else if (choice > 0 && choice <= infrastructure.getNewsList().size()) {
                News selectedNews = infrastructure.getNewsList().get(choice - 1);
                selectedNews.displayNews();
            } else {
                System.out.println("Please Enter Another Number");
            }
        }


    }
}
