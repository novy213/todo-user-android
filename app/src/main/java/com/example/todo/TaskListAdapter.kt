import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.todo.R
import com.example.todo.Task

class TaskListAdapter(private val tasks: List<Task>) : BaseAdapter() {

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_task, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val task = tasks[position]

        viewHolder.taskDescriptionTextView.text = task.description

        if (task.done == 1) {
            viewHolder.taskDescriptionTextView.setTextColor(Color.GREEN)
        } else {
            viewHolder.taskDescriptionTextView.setTextColor(Color.BLACK)
        }

        return view!!
    }

    private class ViewHolder(view: View) {
        val taskDescriptionTextView: TextView = view.findViewById(R.id.taskDescriptionTextView)
    }
}