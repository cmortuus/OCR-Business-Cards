import com.biz.cards.ContactInfo;
import com.biz.cards.main;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Tests {


    @Test
    public void givenTestCase0(){
        ContactInfo test0 = main.businessCardParser("ASYMMETRIK LTD\n" +
                "Mike Smith\n" +
                "Senior Software Engineer\n" +
                "(410)555-1234\n" +
                "msmith@asymmetrik.com");
        assertEquals(test0.getName(), "Name: Mike Smith");
        assertEquals(test0.getPhoneNumber(), "Phone: 4105551234");
        assertEquals(test0.getEmailAddress(), "Email: msmith@asymmetrik.com");
    }


    @Test
    public void givenTestCase1(){
        ContactInfo test0 = main.businessCardParser("Foobar Technologies\n" +
                "Analytic Developer\n" +
                "Lisa Haung\n" +
                "1234 Sentry Road\n" +
                "Columbia, MD 12345\n" +
                "Phone: 410-555-1234\n" +
                "Fax: 410-555-4321\n" +
                "lisa.haung@foobartech.com");
        assertEquals(test0.getName(), "Name: Lisa Haung");
        assertEquals(test0.getPhoneNumber(), "Phone: 4105551234");
        assertEquals(test0.getEmailAddress(), "Email: lisa.haung@foobartech.com");

    }


    @Test
    public void givenTestCase2(){
        ContactInfo test0 = main.businessCardParser("Arthur Wilson\n" +
                "Software Engineer\n" +
                "Decision & Security Technologies\n" +
                "ABC Technologies\n" +
                "123 North 11th Street\n" +
                "Suite 229\n" +
                "Arlington, VA 22209\n" +
                "Tel: +1 (703) 555-1259\n" +
                "Tel: +1 (703) 555-1259\n" +
                "Tel: +1 (703) 555-1259\n" +
                "awilson@abctech.com");
        assertEquals(test0.getName(), "Name: Arthur Wilson");
        assertEquals(test0.getPhoneNumber(), "Phone: 17035551259");
        assertEquals(test0.getEmailAddress(), "Email: awilson@abctech.com");
    }


    @Test
    public void customTestCase0(){
        try {
            main.businessCardParser("");
        }catch (IllegalStateException e){
            assertEquals(e.getMessage(), "We cannot find the needed information to create a contact.");
        }
    }

    @Test
    public void customTestCase1(){
        try {
            main.ocrBizCardGenerater("bizCardThatDoesNotExist.png");
        }catch (IllegalStateException e){
            assertEquals(e.getMessage(), "That image does not exist");
        }
    }


    void printValues(ContactInfo contactInfo){
        System.out.println(contactInfo.getName());
        System.out.println(contactInfo.getPhoneNumber());
        System.out.println(contactInfo.getEmailAddress());
    }


}
