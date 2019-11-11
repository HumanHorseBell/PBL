package kjharu.com.pbl2firebasedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategorizedActivity : AppCompatActivity() {
    var items = ArrayList<Goods>()
    lateinit var adapter:ArrayAdapter<Goods>
    lateinit var itemList:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorized)

        val category = intent.getStringExtra("category")
        val productDatabase = FirebaseDatabase.getInstance().reference.child("product")

        adapter = ArrayAdapter<Goods>(this, android.R.layout.simple_list_item_1, items);
        itemList = findViewById(R.id.itemList)
        itemList.adapter = adapter

        val singleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(child in dataSnapshot.children) {
                    if(child.child("sort").value.toString().equals(category)) {
                        items.add(Goods(child.key.toString(), child.child("name").value.toString()))
                    }
                }
                adapter.notifyDataSetChanged()
                adapter.notifyDataSetInvalidated()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        productDatabase.addListenerForSingleValueEvent(singleListener)
    }
}
