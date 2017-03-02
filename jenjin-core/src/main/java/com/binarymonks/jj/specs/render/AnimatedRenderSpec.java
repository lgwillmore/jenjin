package com.binarymonks.jj.specs.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.AnimationProvider;
import com.binarymonks.jj.render.nodes.TextureRenderNode;
import com.binarymonks.jj.things.InstanceParams;


public class AnimatedRenderSpec extends SpatialRenderSpec {

    public BackingTexture backingTexture;
    public int rows;
    public int columns;
    public float width;
    public float height;
    public Array<AnimationSequence> sequences = new Array<>();


    @Override
    public RenderNode makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {
        ObjectMap<String, Animation> animations = new ObjectMap<>();
        Disposable realAsset = getAsset();
        TextureRegion[][] textureRegionGrid = buildTextureRegionGrid(realAsset);
        for (AnimationSequence sequence : sequences) {
            animations.put(sequence.name, buildSequence(textureRegionGrid, sequence));
        }
        AnimationProvider provider = new AnimationProvider(realAsset, animations);
        //RenderSpec spec, TextureProvider provider, float offsetX, float offsetY, float rotationD, float width, float height
        return new TextureRenderNode(
                this,
                provider,
                spatial.getOffsetX(physicsNodeSpec), spatial.getOffsetY(physicsNodeSpec),
                spatial.getRotationD(physicsNodeSpec),
                width, height);
    }

    private Disposable getAsset() {
        if (backingTexture instanceof BackingTexture.Simple) {
            BackingTexture.Simple simple = (BackingTexture.Simple) backingTexture;
            Texture texture = JJ.assets.get(simple.path, Texture.class);
            return texture;
        }
        return null;
    }

    private Animation buildSequence(TextureRegion[][] textureRegionGrid, AnimationSequence sequence) {
        Array<TextureRegion> sequenceFrames = new Array<>();
        int startingColumn = sequence.startFrame % columns;
        int startingRow = sequence.startFrame / columns;
        int endColumn = sequence.endFrame % columns;
        int endRow = (sequence.endFrame / columns);
        int firstRowColLimit = (endRow - startingRow > 0) ? columns : endColumn;
        //Do the first row
        for (int columnIndex = startingColumn; columnIndex < firstRowColLimit; columnIndex++) {
            sequenceFrames.add(textureRegionGrid[startingRow][columnIndex]);
        }
        //Do the middle rows
        for (int rowIndex = startingRow + 1; rowIndex < endRow; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                sequenceFrames.add(textureRegionGrid[rowIndex][colIndex]);
            }
        }
        //Do the last row
        if (endRow > startingRow) {
            for (int columnIndex = 0; columnIndex < endColumn; columnIndex++) {
                sequenceFrames.add(textureRegionGrid[endRow][columnIndex]);
            }
        }
        return new Animation(sequence.duration / (sequence.endFrame - sequence.startFrame), sequenceFrames);
    }

    @Override
    public Array<AssetReference> getAssets() {
        return backingTexture.getAssets();
    }

    private TextureRegion[][] buildTextureRegionGrid(Disposable realAsset) {
        if (realAsset instanceof Texture) {
            Texture texture = (Texture) realAsset;
            TextureRegion[][] regions = TextureRegion.split(texture, texture.getWidth()
                    / columns, texture.getHeight() / rows);
            return regions;
        }
        return null;
    }

}
