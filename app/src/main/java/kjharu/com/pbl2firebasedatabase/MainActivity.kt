package kjharu.com.pbl2firebasedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var searchText: String = ""
    var arraylist = ArrayList<Goods?>()
    var productkeylist = ArrayList<String?>()
    var product: Goods? = null
    //var adapter = null

    // Write a message to the database
    val database = FirebaseDatabase.getInstance().reference.child("product")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //if(arraylist!-)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, this.productkeylist)

        val listView = findViewById<ListView>(R.id.productListview)
        val searchbtn = findViewById<Button>(R.id.searchbtn)


        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            /*val Intent = Intent(this, DetailActivity::class.java)
            Intent.putExtra("goodsNo", arraylist.get(position)?.goodsNo)
            startActivity(Intent)*/
        }

        searchbtn.setOnClickListener{
            val searchedittext = findViewById<EditText>(R.id.searchEdittext)
            searchText =searchedittext.text.toString()
            Toast.makeText(this,searchText,Toast.LENGTH_SHORT).show()
        }

        // Read from the database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productData in dataSnapshot.children) {
                    // child 내에 있는 데이터만큼 반복
                    val productkey = productData.key
                    val productName = productData.child("name").value.toString()

                    if(productkey!=null) {
                        productkeylist.add(productName)
                        val goods = Goods(productkey,productName)
                        arraylist.add(goods)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

}
