package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.*


class D12_shaders : JJGame(MyConfig12.jjConfig) {
    override fun gameOn() {

        JJ.scenes.addSceneSpec("linearLightCube", linearLightCube())
        JJ.scenes.addSceneSpec("normalCube", normalCube())
        JJ.scenes.addSceneSpec("pixelCube", pixelCube())
        JJ.scenes.addSceneSpec("normalCircle", normalCircle())
        JJ.scenes.addSceneSpec("checkeredCircle", checkeredCircle())
        JJ.scenes.loadAssetsNow()

        JJ.render.registerShader(
                name = "linearLight",
                vertexPath = "shaders/default_vertex.glsl",
                fragmentPath = "shaders/linear_light_fragment.glsl"
        )

        JJ.render.registerShader(
                name = "pixels",
                vertexPath = "shaders/default_vertex.glsl",
                fragmentPath = "shaders/pixel_fragment.glsl"
        )

        //Shape shaders are a bit different to texture based shaders ie, no texture
        JJ.render.registerShader(
                name = "checkeredShape",
                vertexPath = "shaders/checkered_shape_vertex.glsl",
                fragmentPath = "shaders/checkered_shape_fragment.glsl"
        )



        JJ.scenes.instantiate(scene {
            nodeRef(params { x = -5f; y = 5f;prop("color", Color.BLUE) }) { "linearLightCube" }
            nodeRef(params { x = 5f; y = 5f;prop("color", Color.ORANGE) }) { "linearLightCube" }
            nodeRef(params { x = -5f; y = 0f;prop("color", Color.BLUE) }) { "pixelCube" }
            nodeRef(params { x = 5f; y = 0f;prop("color", Color.ORANGE) }) { "pixelCube" }
            nodeRef(params { x = 0f; y = 0f;prop("color", Color.GREEN) }) { "checkeredCircle" }
            nodeRef(params { x = -5f; y = -5f;prop("color", Color.BLUE) }) { "normalCube" }
            nodeRef(params { x = 0f; y = -5f;prop("color", Color.GREEN) }) { "normalCircle" }
            nodeRef(params { x = 5f; y = -5f;prop("color", Color.ORANGE) }) { "normalCube" }
        })

    }

    private fun checkeredCircle(): SceneSpecRef {
        return scene {
            render {
                circleRender(1.5f) {
                    color.setOverride("color")
                    shaderPipe = "checkeredShape"
                }
            }
        }
    }

    private fun normalCircle(): SceneSpecRef {
        return scene {
            render {
                circleRender(1.5f) {
                    color.setOverride("color")
                }
            }
        }
    }

    private fun pixelCube(): SceneSpecRef {
        return scene {
            render {
                imageTexture("textures/linear_light_square.png") {
                    width = 3f
                    height = 3f
                    shaderPipe = "pixels"
                    color.setOverride("color")
                }
            }
        }
    }

    private fun normalCube(): SceneSpecRef {
        return scene {
            render {
                imageTexture("textures/linear_light_square.png") {
                    width = 3f
                    height = 3f
                    color.setOverride("color")
                }
            }
        }
    }

    private fun linearLightCube(): SceneSpecRef {
        return scene {
            render {
                imageTexture("textures/linear_light_square.png") {
                    width = 3f
                    height = 3f
                    shaderPipe = "linearLight"
                    color.setOverride("color")
                }
            }
        }
    }
}

object MyConfig12 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.gameView.worldBoxWidth = 20f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}