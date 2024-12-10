import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.go_go_taxy.R

class CustomGridAdapter(
    private val context: Context,
    private val itemNames: Array<String>,
    private val itemImages: Array<Int>
) : BaseAdapter() {

    // Return the minimum size between itemNames and itemImages to avoid index out of bounds
    override fun getCount(): Int = minOf(itemNames.size, itemImages.size)

    override fun getItem(position: Int): Any? =
        if (position in 0 until getCount()) itemNames[position] else null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate the grid item view
        val view: View = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)

        // Bind data to views safely
        val imageView: ImageView = view.findViewById(R.id.grid_item_image)
        val textView: TextView = view.findViewById(R.id.grid_item_text)

        // Ensure position is within bounds before accessing the arrays
        if (position in 0 until getCount()) {
            imageView.setImageResource(itemImages[position])
            textView.text = itemNames[position]
        }

        return view
    }
}
