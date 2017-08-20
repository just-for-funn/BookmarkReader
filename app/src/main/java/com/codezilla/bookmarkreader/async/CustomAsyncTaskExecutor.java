package com.codezilla.bookmarkreader.async;

import android.os.AsyncTask;


import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import com.codezilla.bookmarkreader.exception.*;
/**
 * Created by davut on 7/28/2017.
 */

public class CustomAsyncTaskExecutor<T> extends AsyncTask<Void , Void , T>
{
    private final Callable<T> runnable;
    WeakReference<TaskExecuteOwner<T>> owner = new WeakReference<TaskExecuteOwner<T>>(null);
    private DomainException exception;

    public CustomAsyncTaskExecutor(TaskExecuteOwner<T> owner , Callable<T> runnable)
    {
        this.owner = new WeakReference<TaskExecuteOwner<T>>(owner);
        this.runnable = runnable;
    }


    @Override
    protected T doInBackground(Void... params)
    {
         T result = null;
         try
         {
             result =  this.runnable.call();
         }catch (DomainException domainException) {
            this.exception = domainException;
         }catch (Exception e){
            this.exception= new UnexpectedException(e);
         }
        return result;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if(this.owner.get() != null && !this.isCancelled())
        {
            if(t == null && this.exception != null)
                this.owner.get().onError(exception);
            else
                this.owner.get().onFinish(t);
        }
    }

    public static interface  TaskExecuteOwner<T>
    {
        void onFinish(T t);
        void onError(DomainException domainException);
    }
}
