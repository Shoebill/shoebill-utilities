package net.gtaun.shoebill.util;

import java.util.function.Consumer;

public class TryUtils
{
	public interface ThrowableFunction
	{
		void call() throws Throwable;
	}

	public static void tryTo(ThrowableFunction func)
	{
		tryTo(func, Throwable::printStackTrace);
	}

	public static void tryTo(ThrowableFunction func, Consumer<Throwable> exceptionHandler)
	{
		try
		{
			func.call();
		}
		catch (Throwable ex)
		{
			exceptionHandler.accept(ex);
		}
	}

	public static Consumer<ThrowableFunction> tryToFunc(Consumer<Throwable> exceptionHandler)
	{
		return (func) -> tryTo(func, exceptionHandler);
	}
}
