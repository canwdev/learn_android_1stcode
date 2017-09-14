package com.example.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 创建数据库
    public void onCreateDbClick(View view) {
        Connector.getDatabase();

        Toast.makeText(this, "Connector.getDatabase();", Toast.LENGTH_SHORT).show();
    }

    public void onDeleteDbClick(View view) {
        DataSupport.deleteAll(Book.class);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    // 添加数据
    public void onAddClick(View view) {
        Book book = new Book();

        book.setName("The Da Vinci Code");
        book.setAuthor("Dan Brown");
        book.setPages(454);
        book.setPrice(16.94);
        book.setPress("Litepal Yes!");
        book.save();
        book.setPrice(16.96);

        Book book1 = new Book();
        book1.setName("The Lost Symbol");
        book1.setAuthor("Dan Brown");
        book1.setPages(510);
        book1.setPrice(19.95);
        book1.setPress("Dkjshfuo Ahfdish");
        book1.save();

        Toast.makeText(this, "INSERT Complete!!", Toast.LENGTH_SHORT).show();
    }

    // 更新数据
    public void onUpdateClick(View view) {
        Book book = new Book();
        book.setPrice(16.9999);
        book.setToDefault("press");
        book.updateAll("name=? and author=?", "The Lost Symbol", "Dan Brown");

        Toast.makeText(this, "UPDATE Complete!!", Toast.LENGTH_SHORT).show();
    }

    // 删除数据
    public void onDeleteClick(View view) {
        DataSupport.deleteAll(Book.class, "pages>?", "500");

        Toast.makeText(this, "DELETE Complete!!", Toast.LENGTH_SHORT).show();
    }

    // 查询数据
    public void onQueryClick(View view) {
        List<Book> books = DataSupport.findAll(Book.class);
        String allResult = "";
        for (Book book: books) {
            allResult +=
                    book.getId() + " " +
                    book.getName() + " " +
                    book.getAuthor() + " " +
                    book.getPages() + " " +
                    book.getPrice() + " " +
                    book.getPress() + "\n";
        }

        Toast.makeText(this, allResult, Toast.LENGTH_SHORT).show();

    }
}
