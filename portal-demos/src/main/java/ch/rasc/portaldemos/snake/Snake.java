/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.portaldemos.snake;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.github.flowersinthesand.portal.Socket;
import com.google.common.collect.ImmutableMap;

public class Snake {

	private static final int DEFAULT_LENGTH = 5;

	private final String id;

	private final Socket socket;

	private Direction direction;

	private int length = DEFAULT_LENGTH;

	private Location head;

	private final Deque<Location> tail = new ArrayDeque<>();

	private final String hexColor;

	public Snake(String id, Socket socket) {
		this.id = id;
		this.socket = socket;
		this.hexColor = SnakeHandler.getRandomHexColor();
		resetState();
	}

	private void resetState() {
		this.direction = Direction.NONE;
		this.head = SnakeHandler.getRandomLocation();
		this.tail.clear();
		this.length = DEFAULT_LENGTH;
	}

	private synchronized void kill() {
		resetState();
		socket.send("dead");
	}

	private synchronized void reward() {
		length++;
		socket.send("kill");
	}

	public synchronized void update(Collection<Snake> snakes) {
		Location nextLocation = head.getAdjacentLocation(direction);
		if (nextLocation.x >= SnakeHandler.PLAYFIELD_WIDTH) {
			nextLocation.x = 0;
		}
		if (nextLocation.y >= SnakeHandler.PLAYFIELD_HEIGHT) {
			nextLocation.y = 0;
		}
		if (nextLocation.x < 0) {
			nextLocation.x = SnakeHandler.PLAYFIELD_WIDTH;
		}
		if (nextLocation.y < 0) {
			nextLocation.y = SnakeHandler.PLAYFIELD_HEIGHT;
		}
		if (direction != Direction.NONE) {
			tail.addFirst(head);
			if (tail.size() > length) {
				tail.removeLast();
			}
			head = nextLocation;
		}

		handleCollisions(snakes);
	}

	private void handleCollisions(Collection<Snake> snakes) {
		for (Snake snake : snakes) {
			boolean headCollision = id != snake.id && snake.getHead().equals(head);
			boolean tailCollision = snake.getTail().contains(head);
			if (headCollision || tailCollision) {
				kill();
				if (id != snake.id) {
					snake.reward();
				}
			}
		}
	}

	public synchronized Location getHead() {
		return head;
	}

	public synchronized Collection<Location> getTail() {
		return tail;
	}

	public synchronized void setDirection(Direction direction) {
		this.direction = direction;
	}

	public synchronized Map<String, Object> getLocationObjects() {
		List<Point> points = new ArrayList<>();

		points.add(new Point(head.x, head.y));
		for (Location location : tail) {
			points.add(new Point(location.x, location.y));
		}
		return ImmutableMap.of("id", id, "body", points);
	}

	public String getId() {
		return id;
	}

	public String getHexColor() {
		return hexColor;
	}
}
