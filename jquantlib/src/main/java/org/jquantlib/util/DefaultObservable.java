/*
 Copyright (C) 2007 Richard Gomes

 This source code is release under the BSD License.
 
 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollections;

import java.util.List;

import net.jcip.annotations.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// --------------------------------------------------------
// This class is based on the work done by Martin Fischer.
// See references in JavaDoc
//--------------------------------------------------------

/**
 * Default implementation of an {@link Observable}.
 * <p>
 * This implementation notifies the observers in a synchronous fashion. Note that this can cause trouble if you notify observers
 * whilst in a transactional context because once a notification is done it cannot be rolled back.
 * 
 * @note This class is not thread safe
 * 
 * @see <a href="http://www.jroller.com/martin_fischer/entry/a_generic_java_observer_pattern"> Martin Fischer: Observer and
 *      Observable interfaces</a>
 * @see <a href="http://jdj.sys-con.com/read/35878.htm">Improved Observer/Observable</a>
 * 
 * @see Observable
 * @see Observer
 * @see WeakReferenceObservable
 * 
 * @author Richard Gomes
 * @author Srinivas Hasti
 */
@NotThreadSafe
public class DefaultObservable implements Observable {
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultObservable.class);

    //
    // private final fields
    //

    private final ObjectArrayList<Observer> observers;
    private final Observable observable;

    //
    // public constructors
    //

    public DefaultObservable(Observable observable) {
        this.observers = new ObjectArrayList<Observer>();
        if (observable == null)
            throw new NullPointerException("observable is null");
        this.observable = observable;
    }

    //
    // public methods
    //

    public void addObserver(final Observer observer) {
        if (observer == null) throw new NullPointerException("observer is null");
        observers.add(observer);
    }

    public int countObservers() {
        return observers.size();
    }

    public List<Observer> getObservers() {
        return (List<Observer>) ObjectCollections.unmodifiable(this.observers);
    }

    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    public void deleteObservers() {
        observers.clear();
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            wrappedNotify(observer, observable, arg);
        }
    }

    //
    // protected methods
    //

    /**
     * This method is intended to encapsulate the notification semantics, in
     * order to let extended classes to implement their own version. Possible
     * implementations are:
     * <li>remote notification;</li>
     * <li>notification via SwingUtilities.invokeLater</li>
     * <li>others...</li> 
     * 
     * <p>
     * The default notification simply does
     * <pre>
     * observer.update(observable, arg);
     * </pre>
     * 
     * @param observer
     * @param observable
     * @param arg
     */
    protected void wrappedNotify(Observer observer, Observable observable, Object arg) {
        observer.update(observable, arg);
    }

}