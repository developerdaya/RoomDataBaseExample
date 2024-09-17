# How to get a Git project into your build:

## Step 1. Add the JitPack repository to your build file
```
fun main()
{
    val n = 5
    val rowLength = 2*n-1
    val columnLength  = 3*n
    val middleItem = n+1/2
    val numberOfSpace = n - ((n + 1) / 2)

    for (i in 1..rowLength)
    {
        for(j in 1..columnLength)
        {
            if (i==middleItem)
            {
                print("*")
            }
            else
            {
                if (i<middleItem)
                {
                    if (i<=numberOfSpace)
                    {
                        if (j==columnLength)
                        {
                            print("*")

                        }
                        else{
                            print(" ")
                        }
                    }
                    else{
                        if (j==1)
                        {
                            repeat(n)
                            {
                                print(" ")
                            }
                            repeat(n)
                            {
                                print("*")
                            }
                            repeat(n-1)
                            {
                                print(" ")
                            }

                            print("*")

                        }

                    }

                }
                else if (i>middleItem)
                {

                    if (j==1)
                    {
                        print("*")
                    }
                    else{
                        if (i <= rowLength - numberOfSpace)
                        {
                            if (j==2)
                            {
                                repeat(n-1)
                                {
                                    print(" ")
                                }
                                repeat(n)
                                {
                                    print("*")
                                }
                            }
                        }
                    }
                }
            }
        }
        println()
    }
}


```

Add it in your root build.gradle at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
 [![](https://jitpack.io/v/developerdaya/RoomDataBaseExample.svg)](https://jitpack.io/#developerdaya/RoomDataBaseExample)

## Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.developerdaya:RoomDataBaseExample:Tag'
	}

# Room Database Step-by-Step guide

#### 1. Add Dependencies
Sabse pehle, apne `build.gradle` file mein Room dependencies add karo:

```gradle
dependencies {
    def room_version = "2.5.0"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version" // For Java
    kapt "androidx.room:room-compiler:$room_version" // For Kotlin
}
```

#### 2. Define the Entity

Entity ek table ko represent karta hai. Yahaan `Employee` table create karenge:

```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val profile: String,
    val experience: Int,
    val salary: Double
)
```

#### 3. Create DAO Interface

DAO (Data Access Object) queries ko define karta hai.

```kotlin
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {

    // Save an employee
    @Insert
    suspend fun insert(employee: Employee)

    // Fetch all employees
    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<Employee>

    // Delete an employee by ID
    @Query("DELETE FROM employee WHERE id = :employeeId")
    suspend fun deleteById(employeeId: Int)

    // Get employee with maximum salary
    @Query("SELECT * FROM employee ORDER BY salary DESC LIMIT 1")
    suspend fun getMaxSalaryEmployee(): Employee?

    // Get employee with minimum salary
    @Query("SELECT * FROM employee ORDER BY salary ASC LIMIT 1")
    suspend fun getMinSalaryEmployee(): Employee?

    // Get employee with second maximum salary
    @Query("""
        SELECT * FROM employee 
        ORDER BY salary DESC 
        LIMIT 1 OFFSET 1
    """)
    suspend fun getSecondMaxSalaryEmployee(): Employee?
}
```

#### 4. Create Database

Database ko define karte hain:

```kotlin
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "employee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

#### 5. Example Usage

Yeh code example dikhata hai ki kaise `Room Database` ko use kar sakte hain:

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            // Insert new employee
            db.employeeDao().insert(Employee(name = "Ravi Kumar", profile = "Software Engineer", experience = 5, salary = 60000.0))
            db.employeeDao().insert(Employee(name = "Anjali Sharma", profile = "Project Manager", experience = 8, salary = 75000.0))

            // Fetch all employees
            val employees = db.employeeDao().getAllEmployees()
            employees.forEach { println(it) }

            // Get maximum salary employee
            val maxSalaryEmployee = db.employeeDao().getMaxSalaryEmployee()
            println("Max Salary Employee: $maxSalaryEmployee")

            // Get minimum salary employee
            val minSalaryEmployee = db.employeeDao().getMinSalaryEmployee()
            println("Min Salary Employee: $minSalaryEmployee")

            // Get second maximum salary employee
            val secondMaxSalaryEmployee = db.employeeDao().getSecondMaxSalaryEmployee()
            println("Second Max Salary Employee: $secondMaxSalaryEmployee")

            // Delete an employee by ID
            db.employeeDao().deleteById(1)
        }
    }
}
```

### Top 20 Queries for Learning

1. **Get all employees:**
   ```sql
   SELECT * FROM employee
   ```

2. **Get employee by ID:**
   ```sql
   SELECT * FROM employee WHERE id = :employeeId
   ```

3. **Update employee:**
   ```sql
   UPDATE employee SET name = :name, profile = :profile, experience = :experience, salary = :salary WHERE id = :id
   ```

4. **Delete all employees:**
   ```sql
   DELETE FROM employee
   ```

5. **Count employees:**
   ```sql
   SELECT COUNT(*) FROM employee
   ```

6. **Get employees with salary greater than a value:**
   ```sql
   SELECT * FROM employee WHERE salary > :amount
   ```

7. **Get employees with experience less than a value:**
   ```sql
   SELECT * FROM employee WHERE experience < :years
   ```

8. **Get employees by profile:**
   ```sql
   SELECT * FROM employee WHERE profile = :profile
   ```

9. **Get top 5 highest salary employees:**
   ```sql
   SELECT * FROM employee ORDER BY salary DESC LIMIT 5
   ```

10. **Get average salary:**
    ```sql
    SELECT AVG(salary) FROM employee
    ```

11. **Get employees with salaries between two values:**
    ```sql
    SELECT * FROM employee WHERE salary BETWEEN :minSalary AND :maxSalary
    ```

12. **Get employees ordered by name:**
    ```sql
    SELECT * FROM employee ORDER BY name
    ```

13. **Get employees with experience in descending order:**
    ```sql
    SELECT * FROM employee ORDER BY experience DESC
    ```

14. **Find employees by name pattern:**
    ```sql
    SELECT * FROM employee WHERE name LIKE :pattern
    ```

15. **Get employee count by profile:**
    ```sql
    SELECT profile, COUNT(*) FROM employee GROUP BY profile
    ```

16. **Get employees with exact salary:**
    ```sql
    SELECT * FROM employee WHERE salary = :exactSalary
    ```

17. **Get employee with the highest experience:**
    ```sql
    SELECT * FROM employee ORDER BY experience DESC LIMIT 1
    ```

18. **Get employees with salary less than average:**
    ```sql
    SELECT * FROM employee WHERE salary < (SELECT AVG(salary) FROM employee)
    ```

19. **Get employees with non-null profiles:**
    ```sql
    SELECT * FROM employee WHERE profile IS NOT NULL
    ```

20. **Delete employees with experience less than a value:**
    ```sql
    DELETE FROM employee WHERE experience < :years
    ```

Screenshots
![Uploading image.png…]()
_________________________________________________________________________________________
![image](https://github.com/user-attachments/assets/35ea3c77-0054-4beb-8e19-321489f5ebe0)
# Happy Coding : 💗

 
