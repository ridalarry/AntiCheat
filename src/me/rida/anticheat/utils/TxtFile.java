package me.rida.anticheat.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class TxtFile {
	private File File;
	private final String Name;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final List<String> Lines = new ArrayList();

	public TxtFile(JavaPlugin Plugin, String Path, String Name) {
		this.File = new File(Plugin.getDataFolder() + Path);
		this.File.mkdirs();
		this.File = new File(Plugin.getDataFolder() + Path, Name + ".txt");
		try {
			this.File.createNewFile();
		} catch (final IOException localIOException) {
		}
		this.Name = Name;

		readTxtFile();
	}

	public void clear() {
		this.Lines.clear();
	}

	public void addLine(String line) {
		this.Lines.add(line);
	}

	public void write() {
		try {
			final FileWriter fw = new FileWriter(this.File, false);
			final BufferedWriter bw = new BufferedWriter(fw);
			for (final String Line : this.Lines) {
				bw.write(Line);
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (final Exception localException) {
		}
	}

	public void readTxtFile() {
		this.Lines.clear();
		try {
			final FileReader fr = new FileReader(this.File);
			final BufferedReader br = new BufferedReader(fr);
			String Line;
			while ((Line = br.readLine()) != null) {
				this.Lines.add(Line);
			}
			br.close();
			fr.close();
		} catch (final Exception exx) {
			exx.printStackTrace();
		}
	}

	public String getName() {
		return this.Name;
	}

	public String getText() {
		String text = "";
		for (int i = 0; i < this.Lines.size(); i++) {
			final String line = this.Lines.get(i);

			text = text + line + (this.Lines.size() - 1 == i ? "" : "\n");
		}
		return text;
	}

	public List<String> getLines() {
		return this.Lines;
	}
}