package kjharu.com.pbl2firebasedatabase

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
    val database = FirebaseDatabase.getInstance().getReference().child("product")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //if(arraylist!-)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, this.productkeylist)

        val listView = findViewById<ListView>(R.id.productListview)
        val searchbtn = findViewById<Button>(R.id.searchbtn)


        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

        }

        searchbtn.setOnClickListener{
            val searchedittext = findViewById<EditText>(R.id.searchEdittext)
            searchText =searchedittext.text.toString()
            Toast.makeText(this,searchText,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()

        // Read from the database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productData in dataSnapshot.children) {
                    // child 내에 있는 데이터만큼 반복
                    val productkey = productData.key
                    val productCategory = productData.child("category").getValue().toString()
                    val productPrice = productData.child("price").getValue().toString().toInt()

                    Toast.makeText(this@MainActivity,productCategory+", "+productPrice,Toast.LENGTH_SHORT).show()
                    if(productkey!=null) {
                        productkeylist.add(productkey)
                        val goods = Goods(productkey,productCategory,productPrice)
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
