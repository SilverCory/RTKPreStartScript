/*
 * http://ryred.co/
 * ace[at]ac3-servers.eu
 *
 * =================================================================
 *
 * Copyright (c) 2016, Cory Redmond
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of RTKPreStartScript nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package co.ryred.rtk.prestartscript;

import com.drdanick.McRKit.ToolkitEvent;
import com.drdanick.McRKit.module.Module;
import com.drdanick.McRKit.module.ModuleLoader;
import com.drdanick.McRKit.module.ModuleMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Cory Redmond &lt;ace@ac3-servers.eu&gt;
 */
public class PreStartScriptModule extends Module {

	public PreStartScriptModule( ModuleMetadata moduleMetadata, ModuleLoader moduleLoader, ClassLoader classLoader ) {
		super( moduleMetadata, moduleLoader, classLoader, ToolkitEvent.ON_SERVER_RESTART, ToolkitEvent.ON_SERVER_HOLD );
	}

	@Override
	protected void onEnable() {

		String command = "bash starting.sh";
		if ( System.getProperty( "RTKStartupCommand" ) != null ) {
			command = System.getProperty( "RTKStartupCommand" );
		}

		ProcessBuilder processBuilder = new ProcessBuilder( command.split( " " ) );

		System.out.println( "Starting \"" + command + "\"." );

		try {
			Process proc = processBuilder.start();

			BufferedReader br = new BufferedReader( new InputStreamReader( proc.getInputStream() ) );
			String line;
			while ( (line = br.readLine()) != null ) {
				System.out.println( "{OUT} " + line );
			}

			System.out.println( "Exit status: " + proc.waitFor() );

		} catch ( IOException | InterruptedException e ) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDisable() {

	}


}
