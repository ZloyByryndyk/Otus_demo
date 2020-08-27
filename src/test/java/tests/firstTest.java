package tests;
import PageObject.Main;
import initPoint.CoreTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class firstTest extends CoreTestCase {

    public Main MainMethods;

    public void setUp() throws Exception
    {
        super.setUp();
        MainMethods = new Main(driver);
    }

    // Test анотация для Junit
    @Test
    //тест который жмёт  кнопку поиска, вбивает в input значение "film", а потом выбираем конкретный фильм из появившегося списка
    public  void test_find_film() {
        search.initSearch();
        search.searchInput("film");
        search.clickFilm("A Serbian Film");
    }

    @Test
    public void test_cancel_search () {
        search.initSearch();
        search.searchInput("");
        Main.waitForElementAndClick(
                By.id("Collapse"),
                "Не могу найти кнопку назад",
                5
        );
        Main.waitForElementAndClick(
                By.id("Navigate up"),
                "Не могу найти вторую кнопку назад",
                5
        );
        Main.waitForElementNotPresentById(
                By.id("dev.akat.filmreel:id/search_src_text"),
                "Инпут с предыдущего экрана все еще есть",
                5
        );
    }

    @Test
    public void test_assert () {
        Main.waitForElementAndClick(
                By.id("dev.akat.filmreel:id/menu_search_action"),
                "Не смог найти кнопку поиска",
                5
        );
        Main.waitForElementAndSendKeys(
                By.id("dev.akat.filmreel:id/search_src_text"),
                "film",
                "Не смог найти инпут для ввода текста"
        );
        Main.waitForElementAndClick(
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[1]"),
                "Не могу найти конкретный фильм",
                5
        );
        WebElement film_text = Main.waitForElement(
                By.id("dev.akat.filmreel:id/movie_overview"),
                "Не могу найти описание фильма"
        );

        String actual = film_text.getText();

        Assert.assertEquals(
                "не совпадает",
                "Milos, a retired porn star, leads a normal family life trying to make ends meet. Presented with the opportunity of a lifetime to financially support his family for the rest of their lives, Milos must participate in one last mysterious film. From then on, Milos is drawn into a maelstrom of unbelievable cruelty and mayhem.",
                 actual
        );
    }

    @Test
    public void test_search_and_clear()
    {

        Main.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc=\"Search\"]"),
                "Не смог найти кнопку поиска",
                5
        );
        Main.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='dev.akat.filmreel:id/search_src_text']"),
                "film",
                "Не смог найти инпут для ввода текста"
        );

        WebElement clear = Main.waitForElementAndClear(
                By.xpath("//*[@resource-id='dev.akat.filmreel:id/search_src_text']"),
                "Не смог очистить инпут",
                5
        );

        String assert_clear = clear.getText();
        Assert.assertEquals(
                "Текст все еще есть",
                "   ",
                assert_clear
        );
    }

    @Test
    public void test_swipe ()
    {
        swipe.swipeToCurrentElement("The Hunt");
    }
}




