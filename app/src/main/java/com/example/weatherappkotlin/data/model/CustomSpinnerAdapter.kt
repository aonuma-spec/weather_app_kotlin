import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.weatherappkotlin.data.model.Place

/**
 * 地域一覧の表示名と値
 */
class CustomSpinnerAdapter(context: Context, resource: Int, items: List<Place>) :
    android.widget.ArrayAdapter<Place>(context, resource, items) {

    // プルダウンを閉じたときに表示されるビューの設定
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    // プルダウンを展開したときの各項目のビューの設定
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    // ビューの再利用または新規作成
    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        // ここでデータクラスの name (表示名) をセットする
        textView.text = item?.name

        return view
    }
}
