package designpatterns.builder;

public class HeroBuilder extends ActorBuilder {
	public void buildType() {
		actor.setType("Hero");
	}

	public void buildSex() {
		actor.setSex("Male");
	}

	public void buildFace() {
		actor.setFace("Handsome");
	}

	public void buildCostume() {
		actor.setCostume("A helmet and armor");
	}

	public void buildHairstyle() {
		actor.setHairstyle("Elegant");
	}
}
