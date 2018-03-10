package com.codezilla.bookmarkreader.async;

import android.os.AsyncTask;
import android.util.Log;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;
import com.codezilla.bookmarkreader.exception.DomainException;
import com.codezilla.bookmarkreader.exception.UnexpectedException;

import static com.annimon.stream.Optional.ofNullable;

/**
 * Created by davut on 7/28/2017.
 */

public class CustomAsyncTaskExecutor<T> extends AsyncTask<Void , Void , T>
{
    private Supplier<T> runnable;
    private DomainException exception;

    Optional<Consumer<T>> onSuccess;
    Optional<Consumer<DomainException>> onError;
    public CustomAsyncTaskExecutor(Supplier<T> runnable)
    {
        this.runnable = runnable;
    }


    public CustomAsyncTaskExecutor<T> onSuccess(Consumer<T> consumer)
    {
        this.onSuccess = ofNullable(consumer);
        return this;
    }

    public CustomAsyncTaskExecutor<T> onError(Consumer<DomainException> handler)
    {
        this.onError = ofNullable(handler);
        return this;
    }

    public static <T> CustomAsyncTaskExecutor<T> async(Supplier<T> callable )
    {
        return new CustomAsyncTaskExecutor<T>(callable);
    }

    @Override
    protected T doInBackground(Void... params)
    {
         T result = null;
         try
         {
             result =  this.runnable.get();
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
        if(!this.isCancelled())
        {
            if(t == null && this.exception != null)
                this.onError.ifPresent(o->o.accept(exception));
            else
                this.onSuccess.ifPresent(o->o.accept(t));
        }
    }

    @Override
    protected void onCancelled()
    {
        Log.i(getClass().getSimpleName(),"Task canceled");
        onSuccess = null;
        onError = null;
        this.runnable = null;
        this.exception =null;
        super.onCancelled();
    }
}
