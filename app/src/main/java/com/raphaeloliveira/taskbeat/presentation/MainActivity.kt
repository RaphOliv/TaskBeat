package com.raphaeloliveira.taskbeat.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raphaeloliveira.taskbeat.R
import com.raphaeloliveira.taskbeat.TaskBeatApplication
import com.raphaeloliveira.taskbeat.data.CategoryEntity
import com.raphaeloliveira.taskbeat.data.TaskBeatDataBase
import com.raphaeloliveira.taskbeat.data.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var categories = listOf<CategoryUiData>()
    private var tasks = listOf<TaskUiData>()
    private var categoriesEntity = listOf<CategoryEntity>()

    private lateinit var onDeleteClicked: (TaskUiData) -> Unit
    private lateinit var rvCategory: RecyclerView
    private lateinit var ctnContent: LinearLayout
    private lateinit var fabCreateTask: FloatingActionButton
    private var ALL: String = "ALL"

    private val categoryAdapter = CategoryListAdapter()
    private val taskAdapter by lazy {
        TaskListAdapter()
    }

    lateinit var db: TaskBeatDataBase

    private val categoryDao by lazy {
        db.getCategoryDao()
    }

    private val taskDao by lazy {
        db.getTaskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCategory = findViewById(R.id.rv_categories)
        ctnContent = findViewById(R.id.ctn_content)
        fabCreateTask = findViewById(R.id.fab_create_task)
        val rvTask = findViewById<RecyclerView>(R.id.rv_tasks)
        val btnCreateEmpty = findViewById<Button>(R.id.btn_create_empty)

        ALL = getString(R.string.all_category)
        val categoryDeleted = getString(R.string.category_deleted)
        val taskDeleted = getString(R.string.task_deleted)

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!
        val swipeBackground = ColorDrawable(Color.RED)

        btnCreateEmpty.setOnClickListener {
            showCreateCategoryBottomSheet()
        }

        fabCreateTask.setOnClickListener {
            showCreateUpdateTaskBottomSheet()

            /*TaskBeatApplication.TaskBeatGlobals.selectedCategory = ALL*/

            val categoryTemp = categories.map { item ->
                when {
                    item.name == ALL -> item.copy(isSelected = true)
                    item.isSelected -> item.copy(isSelected = false)
                    else -> item
                }
            }

            /*GlobalScope.launch(Dispatchers.IO) {
                getTasksFromDatabase()
            }*/

            categoryAdapter.submitList(categoryTemp)

        }

        taskAdapter.setOnClickListener {task ->
            showCreateUpdateTaskBottomSheet(task)
        }

        categoryAdapter.setOnLongClickListener { categoryToBeDeleted ->
            if(categoryToBeDeleted.name != "+" && categoryToBeDeleted.name != (ALL)){
                val title = this.getString(R.string.category_delete_title)
                val message = this.getString(R.string.category_delete_message)
                val btnAction = this.getString(R.string.delete)

                showInfoDialog(
                    title = title,
                    message = message,
                    action = btnAction,
                ) {
                    val categoryEntityToBeDeleted = CategoryEntity(
                        name = categoryToBeDeleted.name,
                        isSelected = categoryToBeDeleted.isSelected
                    )
                    deleteCategory(categoryEntityToBeDeleted)
                    Toast.makeText(this, (categoryDeleted), Toast.LENGTH_SHORT).show()
                }
            }
        }

        categoryAdapter.setOnClickListener { selected ->
            if (selected.name == "+") {
                showCreateCategoryBottomSheet()
            } else {
                val categoryTemp = categories.map { item ->
                    when {
                        item.name == selected.name && item.isSelected -> item.copy(isSelected = true)

                        item.name == selected.name && !item.isSelected -> item.copy(isSelected = true)
                        item.name != selected.name && item.isSelected -> item.copy(isSelected = false)
                        else -> item
                    }
                }

                    if (selected.name != (ALL)) {
                        filterTasksByCategoryName(selected.name)
                    } else {
                        GlobalScope.launch(Dispatchers.IO) {
                        getTasksFromDatabase()
                        }
                    }

                categoryAdapter.submitList(categoryTemp)
            }

        }

        rvCategory.adapter = categoryAdapter
        rvTask.adapter = taskAdapter

        onDeleteClicked = { task ->
            val taskEntityToBeDeleted = TaskEntity(
                id = task.id,
                name = task.name,
                category = task.category
            )
            deleteTask(taskEntityToBeDeleted)
            Toast.makeText(this, (taskDeleted), Toast.LENGTH_SHORT).show()
        }

            val itemTouchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val taskViewHolder = viewHolder as TaskListAdapter.TaskViewHolder
                        val task = taskViewHolder.getTask()
                        onDeleteClicked.invoke(task)
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )

                        val itemView = viewHolder.itemView
                        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                        val iconTop =
                            itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                        val iconBottom = iconTop + deleteIcon.intrinsicHeight

                        if (dX < 0) {
                            val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                            val iconRight = itemView.right - iconMargin
                            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                            swipeBackground.setBounds(
                                itemView.right + dX.toInt(),
                                itemView.top,
                                itemView.right,
                                itemView.bottom
                            )
                        } else {
                            swipeBackground.setBounds(0, 0, 0, 0)
                            deleteIcon.setBounds(0, 0, 0, 0)
                        }

                        swipeBackground.draw(c)
                        deleteIcon.draw(c)
                    }
                }

            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(rvTask)
    }

    override fun onStart() {
        super.onStart()

        db = (application as TaskBeatApplication).db

        GlobalScope.launch(Dispatchers.IO) {
            getCategoriesFromDatabase()
            getTasksFromDatabase()
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.IO) {
            getCategoriesFromDatabase()
            getTasksFromDatabase()
        }
    }

    private fun showInfoDialog(
        title: String,
        message: String,
        action: String,
        onActionClicked: () -> Unit
    ) {
        val infoBottomSheet = InfoBottomSheet(
            title = title,
            message = message,
            action = action,
            onActionClicked = onActionClicked
        )
        infoBottomSheet.show(
            supportFragmentManager,
            "infoBottomSheet")
    }

    private fun getCategoriesFromDatabase() {
        val categoriesFromDb: List<CategoryEntity> = categoryDao.getAll()
        categoriesEntity = categoriesFromDb

        GlobalScope.launch(Dispatchers.Main){
            if(categoriesEntity.isEmpty()){
                rvCategory.isVisible = false
                fabCreateTask.isVisible = false
                ctnContent.isVisible = true
            }else{
                rvCategory.isVisible = true
                fabCreateTask.isVisible = true
                ctnContent.isVisible = false
            }
        }

        val categoriesUiData = categoriesFromDb.map {
            CategoryUiData(
                name = it.name,
                isSelected = it.isSelected
            )
        }
            .toMutableList()

        categoriesUiData.add(
            CategoryUiData(
                name = "+",
                isSelected = false
            )
        )

            val tempCategoryList = mutableListOf(
                CategoryUiData(
                    name = (ALL),
                    isSelected = true
                )
            )

        tempCategoryList.addAll(categoriesUiData)
        GlobalScope.launch(Dispatchers.Main) {
            categories = tempCategoryList
            categoryAdapter.submitList(categories)
        }
    }

    private fun getTasksFromDatabase() {
        val tasksFromDb: List<TaskEntity> = taskDao.getAll()
        val tasksUiData = tasksFromDb.map {
            TaskUiData(
                    id = it.id,
                    name = it.name,
                    category = it.category
            )
        }

        GlobalScope.launch(Dispatchers.Main) {
            tasks = tasksUiData
            taskAdapter.submitList(tasksUiData)
        }
    }

    private fun insertCategory(categoryEntity: CategoryEntity){
        GlobalScope.launch(Dispatchers.IO) {
            categoryDao.insert(categoryEntity)
            getCategoriesFromDatabase()
        }
    }

    private fun insertTask(taskEntity: TaskEntity){
        GlobalScope.launch(Dispatchers.IO) {
            taskDao.insert(taskEntity)
            getTasksFromDatabase()
        }
    }

    private fun updateTask(taskEntity: TaskEntity){
        GlobalScope.launch(Dispatchers.IO) {
            taskDao.update(taskEntity)
            getTasksFromDatabase()
        }
    }

    private fun deleteTask(taskEntity: TaskEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            taskDao.delete(taskEntity)
            getTasksFromDatabase()
        }
    }

    private fun deleteCategory(categoryEntity: CategoryEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            val tasksToBeDeleted = taskDao.getAllByCategoryName(categoryEntity.name)
            taskDao.deleteAll(tasksToBeDeleted)
            categoryDao.delete(categoryEntity)
            getCategoriesFromDatabase()
            getTasksFromDatabase()
        }
    }

    private fun filterTasksByCategoryName(categoryName: String){
        GlobalScope.launch(Dispatchers.IO) {
            val tasksFromDb: List<TaskEntity> = taskDao.getAllByCategoryName(categoryName)
            val tasksUiData = tasksFromDb.map {
                TaskUiData(
                    id = it.id,
                    name = it.name,
                    category = it.category
                )
            }

            GlobalScope.launch(Dispatchers.Main) {
                taskAdapter.submitList(tasksUiData)
            }
        }
    }

    private fun showCreateUpdateTaskBottomSheet(taskUiData: TaskUiData? = null) {
        val createTaskBottomSheet = CreateOrUpdateTaskBottomSheet(
            task = taskUiData,
            categoryList = categoriesEntity,
            onCreateClicked = {
                    taskToBeCreated ->
                val taskEntityToBeInserted = TaskEntity(
                    name = taskToBeCreated.name,
                    category = taskToBeCreated.category
                )
                insertTask(taskEntityToBeInserted)
            },
            onUpdateClicked = {
                    taskToBeUpdated ->
                val taskEntityToBeUpdated = TaskEntity(
                    id = taskToBeUpdated.id,
                    name = taskToBeUpdated.name,
                    category = taskToBeUpdated.category
                )
                updateTask(taskEntityToBeUpdated)
            },
            onDeleteClicked = onDeleteClicked
        )
        createTaskBottomSheet.show(
            supportFragmentManager,
            "create_task")
    }

    private fun showCreateCategoryBottomSheet() {
        val createCategoryBottomSheet = CreateCategoryBottomSheet{ categoryName ->
            val categoryEntity = CategoryEntity(
                name = categoryName,
                isSelected = false
            )

            insertCategory(categoryEntity)
        }
        createCategoryBottomSheet.show(supportFragmentManager, "create_category")
    }
}

