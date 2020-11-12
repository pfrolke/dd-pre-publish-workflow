/**
 * Copyright (C) 2020 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.dd.wf.dataverse

import java.io.PrintStream

import nl.knaw.dans.lib.logging.DebugEnhancedLogging

import scala.util.Try

class DataverseInstance(config: DataverseInstanceConfig)(implicit resultOutputStream: PrintStream) extends DebugEnhancedLogging {
  def checkConnection(): Try[Unit] = {
    logger.info("Checking if root dataverse can be reached...")
    dataverse("root").view().map {
      _ =>
        logger.info("OK: root dataverse is reachable.")
        ()
    }
  }

  def dataverse(dvId: String): Dataverse = {
    new Dataverse(dvId: String, config)
  }

  def dataset(id: String, isPersistentId: Boolean): Dataset = {
    new Dataset(id, isPersistentId, config)
  }

  def file(id: String, isPersistentId: Boolean): FileCommand = {
    new FileCommand(id, isPersistentId, config)
  }

  def workflows(): Workflows = {
    new Workflows(config)
  }
}
