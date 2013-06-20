/*
 Copyright (C) 2013 Thomas Bardoux, Christophe Venevongsos

 This file is part of Liquid Wars Android

 Liquid Wars Android is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Liquid Wars Android is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package fr.umlv.tbcv.liquidwar.logic.pathfinding;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * A TreeSet with an update method (only works for Node)
 */
public class TreeNodeSet<E> extends TreeSet<E> implements Iterable<E>{

    public TreeNodeSet(Comparator<? super E> comparator) {
        super(comparator) ;
    }

    public boolean update(E e) {
        if(remove(e)) { // Current node
            if(add(e)) {
                return true ;
            }
        }
        return false ;
    }

    @Override
    public E pollFirst() {
        return (E) super.pollFirst();
    }
}
