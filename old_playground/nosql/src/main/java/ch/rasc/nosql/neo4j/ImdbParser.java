/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ch.rasc.nosql.neo4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

public class ImdbParser {

	public static void main(String[] args) throws IOException {
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(Paths.get("e:\\temp\\neo4j\\moviedb").toFile());

		long start = System.currentTimeMillis();

		Index<Node> index;
		try (Transaction tx = graphDb.beginTx()) {
			index = graphDb.index().forNodes("myIndex");
			tx.success();
		}
		new ImdbParser().readImdbData(graphDb, index, "E:\\temp\\actresses.list.gz");
		graphDb.shutdown();

		System.gc();

		graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(Paths.get("e:\\temp\\neo4j\\moviedb").toFile());
		try (Transaction tx = graphDb.beginTx()) {
			index = graphDb.index().forNodes("myIndex");
			tx.success();
		}
		new ImdbParser().readImdbData(graphDb, index, "E:\\temp\\actors.list.gz");
		graphDb.shutdown();

		System.out.println((System.currentTimeMillis() - start) / 1000 + " seconds");

	}

	@SuppressWarnings("resource")
	public void readImdbData(GraphDatabaseService graphDb, Index<Node> index,
			String fileName) throws FileNotFoundException, IOException {

		Transaction tx = null;

		try (InputStream is = Files.newInputStream(Paths.get(fileName));
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new GZIPInputStream(is), StandardCharsets.ISO_8859_1))) {

			String line = br.readLine();
			Node currentActorNode = null;

			int count = 0;

			while (line != null) {

				if ("".equals(line.trim())) {
					line = br.readLine();
					continue;
				}

				int actorSep = line.indexOf('\t');
				if (actorSep >= 0) {
					String actor = line.substring(0, actorSep).trim();
					if (!"".equals(actor)) {
						if (tx != null) {
							if (count % 1000 == 0) {
								tx.success();
								tx.close();
								tx = graphDb.beginTx();
							}
						}
						else {
							tx = graphDb.beginTx();
						}
						currentActorNode = graphDb.createNode();
						currentActorNode.setProperty("actor", actor);
						index.add(currentActorNode, "actor", actor);
						count++;
						// System.out.println(actor);

					}

					String title = line.substring(actorSep).trim();
					if (title.length() == 0 || title.contains("{")
							|| title.startsWith("\"") || title.contains("????")
							|| title.contains(" (TV)") || title.contains(" (VG)")
							|| title.contains(" (V)")) {
						line = br.readLine();
						continue;
					}
					int characterStart = title.indexOf('[');
					int characterEnd = title.indexOf(']');
					String character = null;
					if (characterStart > 0 && characterEnd > characterStart) {
						character = title.substring(characterStart + 1, characterEnd);
					}
					int creditStart = title.indexOf('<');
					if (characterStart > 0) {
						title = title.substring(0, characterStart).trim();
					}
					else if (creditStart > 0) {
						title = title.substring(0, creditStart).trim();
					}
					int spaces = title.indexOf("  ");
					if (spaces > 0) {
						if (title.charAt(spaces - 1) == ')'
								&& title.charAt(spaces + 2) == '(') {
							title = title.substring(0, spaces).trim();
						}
					}
					if (character != null && currentActorNode != null) {
						Node movieNode = index.get("title", title).getSingle();
						if (movieNode == null) {
							movieNode = graphDb.createNode();
							movieNode.setProperty("title", title);
							index.add(movieNode, "title", title);
						}

						Relationship relationship = currentActorNode
								.createRelationshipTo(movieNode, RelTypes.ACTS_IN);
						relationship.setProperty("character", character);

					}

				}
				line = br.readLine();
			}

		}
		finally {
			if (tx != null) {
				tx.success();
				tx.close();
			}
		}

	}

}
