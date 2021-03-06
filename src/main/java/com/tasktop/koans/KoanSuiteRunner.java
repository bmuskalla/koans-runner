/*
 * Copyright (c) 2015 Tasktop Technologies.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tasktop.koans;

import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class KoanSuiteRunner extends Suite {

	private final KoanRunListener runListener;

	public KoanSuiteRunner(Class<?> klass) throws InitializationError {
		super(klass, new KoanRunnerBuilder());
		runListener = new KoanRunListener();
	}

	private static class KoanRunnerBuilder extends RunnerBuilder {

		public KoanRunnerBuilder() {
		}

		@Override
		public Runner runnerForClass(Class<?> testClass) throws Throwable {
			RunWith annotation = testClass.getAnnotation(RunWith.class);
			Class<? extends Runner> runnerClass = annotation.value();
			if (runnerClass == KoanRunner.class) {
				return new KoanRunner(testClass);
			}
			throw new IllegalStateException(
					testClass.getName() + " should have a @RunWith annotation with supported Koan Runners");
		}

	}

	@Override
	public void run(RunNotifier notifier) {
		notifier.addListener(runListener);
		super.run(notifier);
	}

}
