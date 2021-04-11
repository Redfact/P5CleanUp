package com.cleanup.todoc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Comparator;

public class TaskWithProject {
    @Embedded
    public Task task;

    @Relation(
            parentColumn = "projectId",
            entityColumn = "id"
    )
    public Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<TaskWithProject> {
        @Override
        public int compare(TaskWithProject left, TaskWithProject right) {
            return left.getProject().getName().compareTo(right.getProject().getName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TaskWithProject> {
        @Override
        public int compare(TaskWithProject left, TaskWithProject right) {
            return right.getProject().getName().compareTo(left.getProject().getName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<TaskWithProject> {
        @Override
        public int compare(TaskWithProject left, TaskWithProject right) {
            return (int) (right.getTask().getCreationTimestamp() - left.getTask().getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<TaskWithProject> {
        @Override
        public int compare(TaskWithProject left, TaskWithProject right) {
            return (int) (left.getTask().getCreationTimestamp() - right.getTask().getCreationTimestamp());
        }
    }

}
