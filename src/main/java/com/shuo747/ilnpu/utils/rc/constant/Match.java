package com.shuo747.ilnpu.utils.rc.constant;

public class Match {
	private int differ;//与模板的差异值
	private int pos;  //匹配的模板序号

	public Match() {
		differ = 1000;
	}

	public Match(int differ, int pos) {
		super();
		this.differ = differ;
		this.pos = pos;
	}


	public int getDiffer() {
		return differ;
	}

	public void setDiffer(int differ) {
		this.differ = differ;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "Match [differ=" + differ + ", pos=" + pos + "]";
	}

	
}
