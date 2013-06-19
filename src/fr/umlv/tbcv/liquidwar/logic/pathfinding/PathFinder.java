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

import java.util.Deque;
import java.util.List;
import java.util.Queue;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidMap;

/**
 * Gives a path to go from point A to point B on a map considering obstacles.
 */
public abstract class PathFinder {
    Heuristic hFunction = new Manhattan();

    public abstract Deque<Coordinates> finder (Coordinates start, Coordinates end);
}
