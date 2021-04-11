package com.cleanup.todoc;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cleanup.todoc.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.DaoTest.WaitViewAction.waitFor;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        int itemCount = listTasks.getAdapter().getItemCount() ;

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche test"));
        onView(withId(android.R.id.button1)).perform(click());
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        assertEquals(itemCount+1,listTasks.getAdapter().getItemCount());
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(itemCount, new DeleteViewAction(){}));
        onView(isRoot()).perform(waitFor(500));
        assertEquals(itemCount,listTasks.getAdapter().getItemCount());
    }

    @Test
    public void sortTasks() {
        MainActivity activity = rule.getActivity();


        //creating project Lucidia
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText("Projet Lucidia")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //creating project Circus
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText("Projet Circus")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //creating project Tartampion
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        int itemCount = listTasks.getAdapter().getItemCount() ;

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));


        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-3, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-1, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Sort AZ project name
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-3, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(itemCount-1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

    }


}
